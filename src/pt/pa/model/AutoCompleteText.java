package pt.pa.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class is used to automatically complete the text in the text fields.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class AutoCompleteText extends TextField {
    private final SortedSet<String> entries;
    private ContextMenu entriesPopup;

    /**
     * Creates an instance of AutoCompleteText.
     */
    public AutoCompleteText() {
        super();
        entries = new TreeSet<>();
        entriesPopup = new ContextMenu();
        textProperty().addListener((observableValue, s, s2) -> {
            if (getText().length() == 0)
            {
                entriesPopup.hide();
            } else
            {
                LinkedList<String> searchResult = new LinkedList<>();
                searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                if (entries.size() > 0)
                {
                    populatePopup(searchResult);
                    if (!entriesPopup.isShowing() && getScene() != null)
                    {
                        entriesPopup.show(AutoCompleteText.this, Side.BOTTOM, 0, 0);
                    }
                } else
                {
                    entriesPopup.hide();
                }
            }
        });

        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> entriesPopup.hide());

    }

    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries
     */
    public SortedSet<String> getEntries() { return entries; }

    /**
     * Populate the entry set with the given search results.
     * Display is limited to 10 entries, for performance reasons.
     *
     * @param searchResult The set of matching strings
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++)
        {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText(result);
                    entriesPopup.hide();
                }
            });
            menuItems.add(item);
        }

        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }
}
