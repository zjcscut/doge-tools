package org.throwable.doge.json.javafx.component;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.fxmisc.richtext.InlineCssTextArea;
import org.throwable.doge.json.common.Constants;
import org.throwable.doge.json.common.Css;
import org.throwable.doge.json.support.AlertViewFactory;
import org.throwable.doge.json.support.JavafxKeyCombinationFactory;
import org.throwable.doge.json.support.JsonShaderEditor;
import org.throwable.doge.json.support.KeyCombinationPressedEventHandler;
import org.throwable.doge.json.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/3 下午10:57
 */
public abstract class SearchDialogBuilder {

	private static final Map<Tab, SearchContainer> SEARCH_CACHE = new ConcurrentHashMap<>();
	private static final Map<Tab, Stage> DIALOG_CACHE = new ConcurrentHashMap<>();

	public static void createAndShowSearchDialog(Tab selectedTab,
												 InlineCssTextArea textArea,
												 AtomicReference<TabPaneBuilder.TabColorResultHolder> tabColorResultHolder) {
		if (!SEARCH_CACHE.containsKey(selectedTab)) {
			SearchContainer searchContainer = new SearchContainer();
			searchContainer.setSelectedTab(selectedTab);
			searchContainer.setTabColorResultHolder(tabColorResultHolder);
			searchContainer.setTextArea(textArea);
			KeyCombinationPressedEventHandler handler = () -> {
				if (!DIALOG_CACHE.containsKey(selectedTab)) {
					searchContainer.showSearchDialog();
				}else {
					DIALOG_CACHE.get(selectedTab).show();
				}
			};
			JavafxKeyCombinationFactory.register(JavafxKeyCombinationFactory.CONTROL_F, handler);
			SEARCH_CACHE.put(selectedTab, searchContainer);
		}
	}

	protected static class SearchContainer {

		private AtomicReference<TabPaneBuilder.TabColorResultHolder> tabColorResultHolder;
		private Tab selectedTab;
		private String md5; //如果字符串变动了需要重建索引
		private InlineCssTextArea textArea;
		private SearchIndexRange currentIndexRange;
		private ArrayList<SearchIndexRange> indexRanges;
		private Integer currentIndex;
		private Integer totalIndexNumber;

		public void resetSearchIndexIfNecessary(String searchContent) {
			String currentText = textArea.getText();
			if (StringUtils.isNotBlank(currentText)) {
				String newMd5 = DigestUtils.sha1Hex(currentText);
				if (!newMd5.equals(md5)) {
					buildSearchIndex(searchContent);
				}
			}
		}

		public void buildSearchIndex(String searchContent) {
			String content = textArea.getText();
			if (StringUtils.isNotBlank(content)) {
				this.md5 = DigestUtils.sha1Hex(content);
				Pattern pattern = Pattern.compile(searchContent);
				Matcher matcher = pattern.matcher(content);
				indexRanges = new ArrayList<>();
				while (matcher.find()) {
					indexRanges.add(new SearchIndexRange(matcher.start(), matcher.end()));
				}
				totalIndexNumber = indexRanges.size();
				if (totalIndexNumber > 0) {
					if (null == currentIndex) {
						currentIndex = 0;
						currentIndexRange = indexRanges.get(currentIndex);
					}

				}
			}
		}

		public void showSearchDialog() {
			Stage stage = new Stage();
			stage.setResizable(false);
			DialogPane dialogPane = new DialogPane();
			Scene scene = new Scene(dialogPane, 600, 130);
			stage.setScene(scene);
			stage.setTitle("Search");
			stage.getIcons().add(new Image(AlertViewFactory.class.getResourceAsStream(Constants.DEFAULT_LOGO_PATH)));
			VBox vBox = new VBox();
			vBox.setPrefWidth(600);
			vBox.setPrefHeight(100);
			HBox searchBar = new HBox();
			searchBar.setPrefWidth(600);
			searchBar.setPrefHeight(50);
			searchBar.setPadding(new Insets(15, 12, 15, 12)); //节点到边缘的距离
			searchBar.setSpacing(10); //节点之间的间距
			Text searchText = new Text("Search Content：");
			searchText.setFont(new Font(20));
			TextField searchContent = new TextField();
			searchContent.setPrefHeight(50);
			searchContent.setPrefWidth(480);
			searchBar.getChildren().addAll(searchText, searchContent);
			HBox buttonBar = new HBox();
			buttonBar.setPrefWidth(600);
			buttonBar.setPrefHeight(50);
			buttonBar.setPadding(new Insets(15, 12, 15, 328)); //节点到边缘的距离
			buttonBar.setSpacing(10); //节点之间的间距
			Button pre = new Button("上一个");
			pre.setOnAction(event -> {
				resetSearchIndexIfNecessary(searchContent.getText());
				registerPreButtonEventAction();
			});
			pre.setPrefWidth(80);
			Button next = new Button("下一个");
			next.setOnAction(event -> {
				resetSearchIndexIfNecessary(searchContent.getText());
				registerNextButtonEventAction();
			});
			next.setPrefWidth(80);
			Button close = new Button("关闭");
			close.setOnAction(event -> {
				SEARCH_CACHE.remove(selectedTab);
				DIALOG_CACHE.remove(selectedTab);
				if (StringUtils.isNotBlank(textArea.getText())) { //重置原来的textArea的着色
					TabPaneBuilder.TabColorResultHolder tabColorResultHolder = this.tabColorResultHolder.get();
					if (null != tabColorResultHolder) {
						List<JsonShaderEditor.ShaderColorResult> colorResultsCache = tabColorResultHolder.getColorResults();
						if (null != colorResultsCache && !colorResultsCache.isEmpty()) {
							for (JsonShaderEditor.ShaderColorResult shaderColorResult : colorResultsCache) {
								textArea.setStyle(shaderColorResult.getFrom(), shaderColorResult.getTo(), shaderColorResult.getFillColor());
							}
						}
					}
				}
				stage.close();
			});
			close.setPrefWidth(80);
			buttonBar.getChildren().addAll(pre, next, close);
			vBox.getChildren().addAll(searchBar, buttonBar);
			dialogPane.setContent(vBox);
			DIALOG_CACHE.put(selectedTab, stage);
			stage.show();
		}

		private void registerPreButtonEventAction() {
			if (null != currentIndex && StringUtils.isNotBlank(textArea.getText())) {
				Integer indexTemp = currentIndex - 1;
				if (indexTemp <= 0) {
					indexTemp = totalIndexNumber - 1;
					//重置上一个索引的着色
					TabPaneBuilder.TabColorResultHolder tabColorResultHolder = this.tabColorResultHolder.get();
					if (null != tabColorResultHolder) {
						List<JsonShaderEditor.ShaderColorResult> colorResultsCache = tabColorResultHolder.getColorResults();
						if (null != colorResultsCache && !colorResultsCache.isEmpty()) {
							for (JsonShaderEditor.ShaderColorResult shaderColorResult : colorResultsCache) {
								textArea.setStyle(shaderColorResult.getFrom(), shaderColorResult.getTo(), shaderColorResult.getFillColor());
							}
						}
					}
					currentIndex = indexTemp;
					currentIndexRange = indexRanges.get(currentIndex);
					textArea.setStyle(currentIndexRange.getFrom(), currentIndexRange.getTo(), Css.HIGHLIGHT_YELLOW);
				} else {
					//重置上一个索引的着色
					TabPaneBuilder.TabColorResultHolder tabColorResultHolder = this.tabColorResultHolder.get();
					if (null != tabColorResultHolder) {
						List<JsonShaderEditor.ShaderColorResult> colorResultsCache = tabColorResultHolder.getColorResults();
						if (null != colorResultsCache && !colorResultsCache.isEmpty()) {
							for (JsonShaderEditor.ShaderColorResult shaderColorResult : colorResultsCache) {
								textArea.setStyle(shaderColorResult.getFrom(), shaderColorResult.getTo(), shaderColorResult.getFillColor());
							}
						}
					}
					currentIndex = indexTemp;
					currentIndexRange = indexRanges.get(currentIndex);
					textArea.setStyle(currentIndexRange.getFrom(), currentIndexRange.getTo(), Css.HIGHLIGHT_YELLOW);
				}
			}
		}

		private void registerNextButtonEventAction() {
			if (null != currentIndex && StringUtils.isNotBlank(textArea.getText())) {
				Integer indexTemp = currentIndex + 1;
				if (indexTemp >= totalIndexNumber) {
					indexTemp = 0;
					//重置上一个索引的着色
					TabPaneBuilder.TabColorResultHolder tabColorResultHolder = this.tabColorResultHolder.get();
					if (null != tabColorResultHolder) {
						List<JsonShaderEditor.ShaderColorResult> colorResultsCache = tabColorResultHolder.getColorResults();
						if (null != colorResultsCache && !colorResultsCache.isEmpty()) {
							for (JsonShaderEditor.ShaderColorResult shaderColorResult : colorResultsCache) {
								textArea.setStyle(shaderColorResult.getFrom(), shaderColorResult.getTo(), shaderColorResult.getFillColor());
							}
						}
					}
					currentIndex = indexTemp;
					currentIndexRange = indexRanges.get(currentIndex);
					textArea.setStyle(currentIndexRange.getFrom(), currentIndexRange.getTo(), Css.HIGHLIGHT_YELLOW);
				} else {
					//重置上一个索引的着色
					TabPaneBuilder.TabColorResultHolder tabColorResultHolder = this.tabColorResultHolder.get();
					if (null != tabColorResultHolder) {
						List<JsonShaderEditor.ShaderColorResult> colorResultsCache = tabColorResultHolder.getColorResults();
						if (null != colorResultsCache && !colorResultsCache.isEmpty()) {
							for (JsonShaderEditor.ShaderColorResult shaderColorResult : colorResultsCache) {
								textArea.setStyle(shaderColorResult.getFrom(), shaderColorResult.getTo(), shaderColorResult.getFillColor());
							}
						}
					}
					currentIndex = indexTemp;
					currentIndexRange = indexRanges.get(currentIndex);
					textArea.setStyle(currentIndexRange.getFrom(), currentIndexRange.getTo(), Css.HIGHLIGHT_YELLOW);
				}
			}
		}

		public String getMd5() {
			return md5;
		}

		public void setMd5(String md5) {
			this.md5 = md5;
		}

		public InlineCssTextArea getTextArea() {
			return textArea;
		}

		public void setTextArea(InlineCssTextArea textArea) {
			this.textArea = textArea;
		}

		public SearchIndexRange getCurrentIndexRange() {
			return currentIndexRange;
		}

		public void setCurrentIndexRange(SearchIndexRange currentIndexRange) {
			this.currentIndexRange = currentIndexRange;
		}

		public ArrayList<SearchIndexRange> getIndexRanges() {
			return indexRanges;
		}

		public void setIndexRanges(ArrayList<SearchIndexRange> indexRanges) {
			this.indexRanges = indexRanges;
		}

		public Integer getCurrentIndex() {
			return currentIndex;
		}

		public void setCurrentIndex(Integer currentIndex) {
			this.currentIndex = currentIndex;
		}

		public Integer getTotalIndexNumber() {
			return totalIndexNumber;
		}

		public void setTotalIndexNumber(Integer totalIndexNumber) {
			this.totalIndexNumber = totalIndexNumber;
		}

		public Tab getSelectedTab() {
			return selectedTab;
		}

		public void setSelectedTab(Tab selectedTab) {
			this.selectedTab = selectedTab;
		}

		public AtomicReference<TabPaneBuilder.TabColorResultHolder> getTabColorResultHolder() {
			return tabColorResultHolder;
		}

		public void setTabColorResultHolder(AtomicReference<TabPaneBuilder.TabColorResultHolder> tabColorResultHolder) {
			this.tabColorResultHolder = tabColorResultHolder;
		}
	}

	protected static class SearchIndexRange {

		private final int from;
		private final int to;

		public SearchIndexRange(int from, int to) {
			this.from = from;
			this.to = to;
		}

		public int getFrom() {
			return from;
		}

		public int getTo() {
			return to;
		}
	}
}
