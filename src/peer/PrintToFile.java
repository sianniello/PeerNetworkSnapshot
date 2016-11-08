package peer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Stefano.
 *         Created 08 nov 2016.
 */
public class PrintToFile {

	private HashMap<Integer, State> hs;

	/**
	 * TODO Put here a description of what this constructor does.
	 * @param hs 
	 *
	 */
	public PrintToFile(HashMap<Integer, State> hs) {
		this.hs = hs;
	}

	@SuppressWarnings("javadoc")
	public void print() {
		File file = new File("snapshot.txt");

		if(file.exists()) {
			file.delete();
			file = new File("snapshot.txt");
		}

		try {
			file.createNewFile();

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(Integer index : hs.keySet())
				bw.write("{" + index + ": " + hs.get(index).toString() + "}\n");
			bw.close();
		} catch (IOException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
	}

}
