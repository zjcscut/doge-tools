package org.throwable.doge.json.javafx.component;

import com.alibaba.fastjson.JSONObject;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;
import org.throwable.doge.json.common.model.JsonTableEntity;
import org.throwable.doge.json.common.model.TreeViewEntity;
import org.throwable.doge.json.javafx.handler.DefaultTabWrapper;
import org.throwable.doge.json.javafx.handler.SceneStageEventHandler;
import org.throwable.doge.json.javafx.handler.TabPaneAddTabEventHandler;
import org.throwable.doge.json.javafx.handler.TabWrapper;
import org.throwable.doge.json.support.*;
import org.throwable.doge.json.utils.StringUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 11:54
 */
public abstract class TabPaneBuilder {

	private static final TabWrapper WRAPPER = new DefaultTabWrapper();
	private static final AtomicReference<Tab> tabSelected = new AtomicReference<>(null);
	public static final AtomicReference<TabColorResultHolder> tabColorResult = new AtomicReference<>(null);
	private static final FastJsonValidator VALIDATOR = new FastJsonValidator();

	public static TabPane createTabPane() {
		TabPane tabPane = new TabPane();
		tabPane.setOnKeyPressed(JavafxKeyCombinationFactory::process);
		JavafxKeyCombinationFactory.register(JavafxKeyCombinationFactory.CONTROL_H, new SceneStageEventHandler());
		Tab tab = new Tab("Welcome");
		tab.setClosable(false);
		tabPane.getSelectionModel().select(tab);
		tabSelected.compareAndSet(null, tab);
		WRAPPER.wrapTab(tab);
		tabPane.getTabs().add(tab);
		registerInlineCssTextAreaEventAction();
		registerTabPaneEventAction(tabPane);
		tabPane.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue)
						-> {
					tabSelected.compareAndSet(oldValue, newValue);
					registerInlineCssTextAreaEventAction();  //每次tab切换要重新注册一次保存事件
				});
		return tabPane;
	}

	private static void registerTabPaneEventAction(TabPane tabPane) {
		TabPaneAddTabEventHandler eventHandler = new TabPaneAddTabEventHandler(tabPane, WRAPPER);
		JavafxKeyCombinationFactory.register(JavafxKeyCombinationFactory.CONTROL_N, eventHandler);

	}

	//估计是InlineCssTextArea自身的bug,使用setOnKeyPressed会触发两次,因此放在TabPane触发事件
	@SuppressWarnings("unchecked")
	private static void registerInlineCssTextAreaEventAction() {
		Tab selectedTab = tabSelected.get();
		if (null != selectedTab) {
			HBox hBox = (HBox) selectedTab.getContent();
			TreeView<TreeViewEntity> treeView = (TreeView<TreeViewEntity>) hBox.getChildren().get(0);
			InlineCssTextArea textArea = ((VirtualizedScrollPane<InlineCssTextArea>) hBox.getChildren().get(1)).getContent();
			TableView<JsonTableEntity> tableView = (TableView<JsonTableEntity>) hBox.getChildren().get(2);
			registerInlineCssTextAreaSaveEventAction(textArea);
			registerInlineCssTextAreaJsonPrettyEventAction(textArea, treeView, tableView);
			registerInlineCssTextAreaJsonMinifyEventAction(textArea);
			registerSearchDialogEventAction(selectedTab, textArea, tabColorResult);
		}
	}

	private static void registerInlineCssTextAreaSaveEventAction(InlineCssTextArea textArea) {
		KeyCombinationPressedEventHandler handler = () -> FileChooserHelper.processSaveFileByTextContent(textArea.getText());
		JavafxKeyCombinationFactory.register(JavafxKeyCombinationFactory.CONTROL_S, handler);
	}

	private static void registerInlineCssTextAreaJsonPrettyEventAction(InlineCssTextArea textArea,
																	   TreeView<TreeViewEntity> treeView,
																	   TableView<JsonTableEntity> tableView) {
		KeyCombinationPressedEventHandler handler = () -> {
			String value = textArea.getText();
			if (StringUtils.isNotBlank(value) && VALIDATOR.wrapValidate(value)) {
				JSONObject jsonObject = JsonFormatHelper.parseToJsonObject(value);
				String json = JsonFormatHelper.prettyToJsonFormat(value);
				textArea.clear();
				textArea.replaceText(json);
				List<JsonShaderEditor.ShaderColorResult> shaderColorResults = JsonShaderEditor.shadeColorForJson(json);
				tabColorResult.compareAndSet(null, new TabColorResultHolder(tabSelected.get(), shaderColorResults));
				if (!shaderColorResults.isEmpty()) {
					for (JsonShaderEditor.ShaderColorResult shaderColorResult : shaderColorResults) {
						textArea.setStyle(shaderColorResult.getFrom(), shaderColorResult.getTo(), shaderColorResult.getFillColor());
					}
				}
				registerTreeViewResetEventAction(treeView, jsonObject, tableView);
			}
		};
		JavafxKeyCombinationFactory.register(JavafxKeyCombinationFactory.CONTROL_P, handler);
	}

	private static void registerTreeViewResetEventAction(TreeView<TreeViewEntity> treeView,
														 JSONObject jsonObject,
														 TableView<JsonTableEntity> tableView) {
		//override to reset root item
		JsonTreeViewParser.parseJsonObjectToTreeViewRootItem(treeView, jsonObject, tableView);
	}

	private static void registerInlineCssTextAreaJsonMinifyEventAction(InlineCssTextArea textArea) {
		KeyCombinationPressedEventHandler handler = () -> {
			String value = textArea.getText();
			if (StringUtils.isNotBlank(value) && VALIDATOR.wrapValidate(value)) {
				textArea.clearStyle(0, value.length());
				textArea.clear();
				textArea.replaceText(JsonFormatHelper.minifyJson(value));
			}
		};
		JavafxKeyCombinationFactory.register(JavafxKeyCombinationFactory.CONTROL_M, handler);
	}

	private static void registerSearchDialogEventAction(Tab selectedTab,
														InlineCssTextArea textArea,
														AtomicReference<TabColorResultHolder> tabColorResultHolder) {
		SearchDialogBuilder.createAndShowSearchDialog(selectedTab, textArea, tabColorResultHolder);
	}

	public static class TabColorResultHolder {

		private Tab tab;
		private List<JsonShaderEditor.ShaderColorResult> colorResults;

		public TabColorResultHolder(Tab tab,
									List<JsonShaderEditor.ShaderColorResult> colorResults) {
			this.tab = tab;
			this.colorResults = colorResults;
		}

		public Tab getTab() {
			return tab;
		}

		public void setTab(Tab tab) {
			this.tab = tab;
		}

		public List<JsonShaderEditor.ShaderColorResult> getColorResults() {
			return colorResults;
		}

		public void setColorResults(List<JsonShaderEditor.ShaderColorResult> colorResults) {
			this.colorResults = colorResults;
		}
	}
}
