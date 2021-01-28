package lib.romsanbryan.utils;

public class FloatUtils {

	public String floatToTime(Float f) {
		if (!validateFloat(f)) {
			return null;
		}

		int h = (int) (float) f;
		int m = (int) (60 * (f - h));

		return String.format("%d:%d", h, m);
	}

	public boolean validateFloat(Float f) {
		boolean isValid = false;
		if (f != null && !f.equals(0.0f)) {
			isValid = true;
		}
		return isValid;
	}

}
