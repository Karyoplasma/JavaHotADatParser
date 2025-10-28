package model;

import java.util.ArrayList;
import java.util.List;
import core.HDATEntry;
import javax.swing.AbstractListModel;

public class HDATEntryListModel extends AbstractListModel<HDATEntry> {

	private static final long serialVersionUID = 5742932381871999107L;
	private final List<HDATEntry> entries;
	private final List<HDATEntry> view;

	public HDATEntryListModel(List<HDATEntry> entries) {
		this.entries = entries;
		this.view = new ArrayList<HDATEntry>(entries);
	}

	@Override
	public int getSize() {
		return this.view.size();
	}

	@Override
	public HDATEntry getElementAt(int index) {
		return this.view.get(index);
	}

	public void filter(String search) {
		view.clear();
		for (HDATEntry entry : entries) {
			if (entry.getName().toLowerCase().contains(search.toLowerCase())) {
				view.add(entry);
			}
		}
		fireContentsChanged(this, 0, getSize());
	}

	public List<HDATEntry> getEntries() {
		return this.entries;
	}

	public void saveEntry(int lastSelectedIndex, HDATEntry entry) {
		if (entry == null || lastSelectedIndex == -1) {
			return;
		}
		this.view.set(lastSelectedIndex, entry);

		int index = this.entries.indexOf(entry);
		if (index >= 0) {
			entries.set(index, entry);
		}
	}
}
