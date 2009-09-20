package imaple.util;

//~--- JDK imports ------------------------------------------------------------

import java.awt.FontMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Manipulates strings.
 * @author David
 */
public class StringManipulator {
    private StringManipulator() {}

    public static String convertToThousands(String s) {
        int places = s.length();

        if (places % 3 == 0) {
            places = (places / 3 - 1);
        } else {
            places /= 3;
        }

        int l = s.length();
        StringBuilder sb = new StringBuilder(s);

        sb.ensureCapacity(l + s.length() / 16);

        for (int i = 0; i < places; i++) {
            sb.insert(l - 3 * (i + 1), ',');
        }

        return sb.toString();
    }

    public static List<String> splitLines(String s, FontMetrics fm, int maxWidth) {
        List<String> ret = new ArrayList<String>();
        String ln = "";
        String[] splitted = s.split(" ");
        StringBuilder nt;

        for (String abc : splitted) {
            nt = new StringBuilder(abc);

            if (fm.stringWidth(ln + nt) >= maxWidth - fm.stringWidth("M")) {
                ret.add(ln);
                ln = "";
                ln += nt.append(" ");
            } else {
                ln += nt.append(" ");
            }
        }

        ret.add(ln);

        // fix the lengths
        for (int i = 0; i < ret.size(); i++) {
            if (fm.stringWidth(ret.get(i)) > maxWidth - fm.stringWidth("M")) {

                // umm
                String the = ret.get(i);
                StringBuilder other = new StringBuilder();

                for (int j = 0; j < the.length(); j++) {
                    char ch = the.charAt(the.length() - 1);

                    the = the.substring(0, the.length() - 1);
                    other.append(ch);
                }

                ret.set(i, the);
                ret.add(i + 1, other.reverse().toString());
                i--;
            }
        }

        return ret;
    }

    public static int getLongestLine(List<String> in, FontMetrics fm) {
        int ret = -1;

        for (String a : in) {
            if (fm.stringWidth(a) > ret) {
                ret = fm.stringWidth(a);
            }
        }

        return ret;
    }

    public static char toUpperCase(char c) {
        switch (c) {
        case 'a' :
            return 'A';
        case 'b' :
            return 'B';
        case 'c' :
            return 'C';
        case 'd' :
            return 'D';
        case 'e' :
            return 'E';
        case 'f' :
            return 'F';
        case 'g' :
            return 'G';
        case 'h' :
            return 'H';
        case 'i' :
            return 'I';
        case 'j' :
            return 'J';
        case 'k' :
            return 'K';
        case 'l' :
            return 'L';
        case 'm' :
            return 'M';
        case 'n' :
            return 'N';
        case 'o' :
            return 'O';
        case 'p' :
            return 'P';
        case 'q' :
            return 'Q';
        case 'r' :
            return 'R';
        case 's' :
            return 'S';
        case 't' :
            return 'T';
        case 'u' :
            return 'U';
        case 'v' :
            return 'V';
        case 'w' :
            return 'W';
        case 'x' :
            return 'X';
        case 'y' :
            return 'Y';
        case 'z' :
            return 'Z';
        default :
            return c;
        }
    }

    public static String xorWith(String in, byte[] bytes) {
        byte[] stringData = in.getBytes();

        for (int i = 0; i < stringData.length; i++) {
            for (int z = 0; z < bytes.length; z++) {
                stringData[i] ^= bytes[z];
            }
        }

        return new String(stringData);
    }

    public static String unXorWith(String in, byte[] bytes) {
        byte[] stringData = in.getBytes();

        for (int i = 0; i < stringData.length; i++) {
            for (int z = bytes.length - 1; z >= 0; z--) {
                stringData[i] ^= bytes[z];
            }
        }

        return new String(stringData);
    }
}
