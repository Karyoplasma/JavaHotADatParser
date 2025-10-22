package model;

import java.util.List;

import javax.swing.AbstractListModel;

public class HDATEntryListModel<HDATEntry> extends AbstractListModel<HDATEntry> {

	private static final long serialVersionUID = 5742932381871999107L;
	private final List<HDATEntry> entries;

	public HDATEntryListModel(List<HDATEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int getSize() {
		return entries.size();
	}

	@Override
	public HDATEntry getElementAt(int index) {
		return entries.get(index);
	}

	public List<HDATEntry> getEntries() {
		return this.entries;
	}

	public void setElementAt(int index, HDATEntry entry) {
		entries.set(index, entry);
		fireContentsChanged(this, index, index);
	}
}
