import java.util.HashMap;
import java.util.Map;
	private static Map<ModificationKind, String> map;
	
	static {
		map = new HashMap<ModificationKind, String>();
		map.put(ModificationKind.NEW, "new file mode");
		map.put(ModificationKind.DELETED, "deleted file mode");
		map.put(ModificationKind.DEFAULT, "nothing");
	}
	
			if(lines.get(modeLine).startsWith(map.get(st))) return st;