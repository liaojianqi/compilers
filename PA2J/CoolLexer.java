/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;
import java.lang.Boolean;
// for recompile


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }
    private int nested_comment = 0;
    private AbstractSymbol filename;
    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
	return filename;
    }
    // mycode start
//    AbstractTable at = new StringTable();
    // mycode end
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int YYSTRING = 2;
	private final int YYCOMMENT = 1;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0,
		85,
		88
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"36,39:7,37:2,34,37:2,35,39:18,37,39,38,39:5,31,32,3,21,18,16,22,20,44:10,26" +
",33,17,1,2,39,30,15,62,14,25,7,19,62,6,4,62:2,13,62,5,12,11,62,8,10,9,42,24" +
",23,62:3,39,40,39:2,61,39,45,46,47,48,49,43,46,50,51,46:2,52,46,53,54,55,46" +
",56,57,41,58,59,60,46:3,28,39,29,27,39,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,166,
"0,1,2,1,3,4,5,6,1:9,7,1:3,8,9,10,11,1:2,12,13:2,14,1:2,13,1:3,13:3,1,13:6,1" +
"5,13:2,15,13:2,1:3,16,17,18,19,15:2,1,15:14,20,21,22,23,24,25,26,27,28,29,3" +
"0,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,5" +
"5,56,57,58,59,60,61,62,63,64,65,66,67,68,13,69,70,71,72,73,74,75,76,77,78,7" +
"9,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,1" +
"5,103,104,105,106")[0];

	private int yy_nxt[][] = unpackFromString(107,63,
"1,2,3,4,5,57,126,133,126,136,126,138,79,140,142,126,6,7,8,83,9,10,11,144,12" +
"6:2,12,13,14,15,16,17,18,19,20,21,3,21,22,3:2,23,126,58,24,161:2,162,161,16" +
"3,161,80,125,132,84,164,161:4,165,3,126,-1:65,25,-1:92,26,-1:34,126,27,126:" +
"4,146,126:5,-1:3,28,-1:3,126:3,-1:15,126:2,28,126:9,27,126:3,146,126:5,-1:1" +
"6,30,-1:47,31,-1:14,32,-1:49,34,-1:94,21,-1,21,-1:26,22:33,35,22,56,22,36,2" +
"2,78,22:22,-1:4,161:2,135,161,137,161:7,-1:3,161,-1:3,161:3,-1:15,161:9,135" +
",161:5,137,161:6,-1:44,24,-1:22,126:2,129,126:9,-1:3,126,-1:3,126:3,-1:15,1" +
"26:9,129,126:12,-1:4,126:12,-1:3,126,-1:3,126:3,-1:15,126:22,-1,30:33,62,-1" +
",30:27,-1:4,161:12,-1:3,161,-1:3,161:3,-1:15,161:22,-1,56:33,40,56:3,40,56," +
"82,56:22,-1:4,126:3,86,126:4,89,126:3,-1:3,126,-1:3,126:3,-1:15,126:8,86,12" +
"6:4,89,126:8,-1:4,63,161:10,139,-1:3,161,-1:3,161:3,-1:15,161:4,139,161:5,6" +
"3,161:11,-1:4,161:2,154,161:9,-1:3,161,-1:3,161:3,-1:15,161:9,154,161:12,-1" +
":32,54,-1:31,22:35,56,22:26,-1:4,126:12,-1:3,29,-1:3,126:3,-1:15,126:2,29,1" +
"26:19,-1:4,161,59,161:4,148,161:5,-1:3,60,-1:3,161:3,-1:15,161:2,60,161:9,5" +
"9,161:3,148,161:5,-1:3,55,-1:60,56:62,-1:4,33,126:11,-1:3,126,-1:3,126:3,-1" +
":15,126:10,33,126:11,-1:4,161:12,-1:3,61,-1:3,161:3,-1:15,161:2,61,161:19,1" +
",53:2,77,53:27,81,53:2,20,21,53,21,53:25,-1:4,126:12,-1:3,126,-1:3,37,126:2" +
",-1:15,126:19,37,126:2,-1:4,161:5,66,161:6,-1:3,161,-1:3,161:3,-1:15,66,161" +
":21,1,3:33,20,21,3,21,3:25,-1:4,126:5,38,126:6,-1:3,126,-1:3,126:3,-1:15,38" +
",126:21,-1:4,161:12,-1:3,161,-1:3,64,161:2,-1:15,161:19,64,161:2,-1:4,126:1" +
"1,103,-1:3,126,-1:3,126:3,-1:15,126:4,103,126:17,-1:4,161:5,65,161:6,-1:3,1" +
"61,-1:3,161:3,-1:15,65,161:21,-1:4,126:6,105,126:5,-1:3,126,-1:3,126:3,-1:1" +
"5,126:16,105,126:5,-1:4,161,69,161:10,-1:3,161,-1:3,161:3,-1:15,161:12,69,1" +
"61:9,-1:4,126:3,107,126:8,-1:3,126,-1:3,126:3,-1:15,126:8,107,126:13,-1:4,1" +
"61:3,47,161:8,-1:3,161,-1:3,161:3,-1:15,161:8,47,161:13,-1:4,126:8,109,126:" +
"3,-1:3,126,-1:3,126:3,-1:15,126:13,109,126:8,-1:4,161:3,72,161:8,-1:3,161,-" +
"1:3,161:3,-1:15,161:8,72,161:13,-1:4,126:5,39,126:6,-1:3,126,-1:3,126:3,-1:" +
"15,39,126:21,-1:4,161:10,67,161,-1:3,161,-1:3,161:3,-1:15,161:6,67,161:15,-" +
"1:4,117,126:11,-1:3,126,-1:3,126:3,-1:15,126:10,117,126:11,-1:4,161:3,68,16" +
"1:8,-1:3,161,-1:3,161:3,-1:15,161:8,68,161:13,-1:4,126:10,41,126,-1:3,126,-" +
"1:3,126:3,-1:15,126:6,41,126:15,-1:4,161:7,71,161:4,-1:3,161,-1:3,161:3,-1:" +
"15,161:14,71,161:7,-1:4,126:3,42,126:8,-1:3,126,-1:3,126:3,-1:15,126:8,42,1" +
"26:13,-1:4,161:9,70,161:2,-1:3,161,-1:3,161:3,-1:15,161:11,70,161:10,-1:4,1" +
"26,43,126:10,-1:3,126,-1:3,126:3,-1:15,126:12,43,126:9,-1:4,161:3,50,161:8," +
"-1:3,161,-1:3,161:3,-1:15,161:8,50,161:13,-1:4,126:9,44,126:2,-1:3,126,-1:3" +
",126:3,-1:15,126:11,44,126:10,-1:4,161:6,73,161:5,-1:3,161,-1:3,161:3,-1:15" +
",161:16,73,161:5,-1:4,126:7,45,126:4,-1:3,126,-1:3,126:3,-1:15,126:14,45,12" +
"6:7,-1:4,161:3,74,161:8,-1:3,161,-1:3,161:3,-1:15,161:8,74,161:13,-1:4,126:" +
"6,120,126:5,-1:3,126,-1:3,126:3,-1:15,126:16,120,126:5,-1:4,161:12,-1:3,161" +
",-1:3,161:2,75,-1:15,161:7,75,161:14,-1:4,126:3,46,126:8,-1:3,126,-1:3,126:" +
"3,-1:15,126:8,46,126:13,-1:4,161:6,76,161:5,-1:3,161,-1:3,161:3,-1:15,161:1" +
"6,76,161:5,-1:4,126:9,121,126:2,-1:3,126,-1:3,126:3,-1:15,126:11,121,126:10" +
",-1:4,126:4,131,126:7,-1:3,126,-1:3,126:3,-1:15,126:15,131,126:6,-1:4,122,1" +
"26:11,-1:3,126,-1:3,126:3,-1:15,126:10,122,126:11,-1:4,126:6,48,126:5,-1:3," +
"126,-1:3,126:3,-1:15,126:16,48,126:5,-1:4,126:3,49,126:8,-1:3,126,-1:3,126:" +
"3,-1:15,126:8,49,126:13,-1:4,126:12,-1:3,126,-1:3,126:2,51,-1:15,126:7,51,1" +
"26:14,-1:4,126:5,124,126:6,-1:3,126,-1:3,126:3,-1:15,124,126:21,-1:4,126:6," +
"52,126:5,-1:3,126,-1:3,126:3,-1:15,126:16,52,126:5,-1:4,161:3,87,161:4,149," +
"161:3,-1:3,161,-1:3,161:3,-1:15,161:8,87,161:4,149,161:8,-1:4,126:11,113,-1" +
":3,126,-1:3,126:3,-1:15,126:4,113,126:17,-1:4,126:6,115,126:5,-1:3,126,-1:3" +
",126:3,-1:15,126:16,115,126:5,-1:4,126:3,118,126:8,-1:3,126,-1:3,126:3,-1:1" +
"5,126:8,118,126:13,-1:4,126:8,111,126:3,-1:3,126,-1:3,126:3,-1:15,126:13,11" +
"1,126:8,-1:4,123,126:11,-1:3,126,-1:3,126:3,-1:15,126:10,123,126:11,-1:4,16" +
"1:3,90,161:4,92,161:3,-1:3,161,-1:3,161:3,-1:15,161:8,90,161:4,92,161:8,-1:" +
"4,126:6,91,126:2,93,126:2,-1:3,126,-1:3,126:3,-1:15,126:11,93,126:4,91,126:" +
"5,-1:4,126:8,119,126:3,-1:3,126,-1:3,126:3,-1:15,126:13,119,126:8,-1:4,161:" +
"3,94,161:8,-1:3,161,-1:3,161:3,-1:15,161:8,94,161:13,-1:4,126:2,95,126:9,-1" +
":3,126,-1:3,126:3,-1:15,126:9,95,126:12,-1:4,161:12,-1:3,161,-1:3,161:3,-1:" +
"15,161,96,161:15,96,161:4,-1:4,126:8,97,126:3,-1:3,126,-1:3,126:3,-1:15,126" +
":13,97,126:8,-1:4,161:9,152,161:2,-1:3,161,-1:3,161:3,-1:15,161:11,152,161:" +
"10,-1:4,126:3,99,126:4,130,126:3,-1:3,126,-1:3,126:3,-1:15,126:8,99,126:4,1" +
"30,126:8,-1:4,161:11,153,-1:3,161,-1:3,161:3,-1:15,161:4,153,161:17,-1:4,12" +
"6:9,127,126,128,-1:3,126,-1:3,126:3,-1:15,126:4,128,126:6,127,126:10,-1:4,1" +
"61:6,98,161:5,-1:3,161,-1:3,161:3,-1:15,161:16,98,161:5,-1:4,126:2,101,126:" +
"9,-1:3,126,-1:3,126:3,-1:15,126:9,101,126:12,-1:4,161:11,100,-1:3,161,-1:3," +
"161:3,-1:15,161:4,100,161:17,-1:4,126:12,-1:3,126,-1:3,126,134,126,-1:15,12" +
"6:18,134,126:3,-1:4,161:6,102,161:5,-1:3,161,-1:3,161:3,-1:15,161:16,102,16" +
"1:5,-1:4,161:12,-1:3,161,-1:3,161,155,161,-1:15,161:18,155,161:3,-1:4,161:8" +
",104,161:3,-1:3,161,-1:3,161:3,-1:15,161:13,104,161:8,-1:4,161:8,106,161:3," +
"-1:3,161,-1:3,161:3,-1:15,161:13,106,161:8,-1:4,156,161:11,-1:3,161,-1:3,16" +
"1:3,-1:15,161:10,156,161:11,-1:4,161:6,108,161:5,-1:3,161,-1:3,161:3,-1:15," +
"161:16,108,161:5,-1:4,161:6,110,161:5,-1:3,161,-1:3,161:3,-1:15,161:16,110," +
"161:5,-1:4,161:3,157,161:8,-1:3,161,-1:3,161:3,-1:15,161:8,157,161:13,-1:4," +
"161:8,158,161:3,-1:3,161,-1:3,161:3,-1:15,161:13,158,161:8,-1:4,161:9,112,1" +
"61:2,-1:3,161,-1:3,161:3,-1:15,161:11,112,161:10,-1:4,161:4,159,161:7,-1:3," +
"161,-1:3,161:3,-1:15,161:15,159,161:6,-1:4,114,161:11,-1:3,161,-1:3,161:3,-" +
"1:15,161:10,114,161:11,-1:4,160,161:11,-1:3,161,-1:3,161:3,-1:15,161:10,160" +
",161:11,-1:4,161:5,116,161:6,-1:3,161,-1:3,161:3,-1:15,116,161:21,-1:4,161:" +
"9,141,161,143,-1:3,161,-1:3,161:3,-1:15,161:4,143,161:6,141,161:10,-1:4,161" +
":6,145,161:2,147,161:2,-1:3,161,-1:3,161:3,-1:15,161:11,147,161:4,145,161:5" +
",-1:4,161:8,150,161:3,-1:3,161,-1:3,161:3,-1:15,161:13,150,161:8,-1:4,161:2" +
",151,161:9,-1:3,161,-1:3,161:3,-1:15,161:9,151,161:12");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */
    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	/* If necessary, add code for other states here, e.g:
	   case COMMENT:
	   ...
	   break;
	*/
    case YYCOMMENT:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, AbstractTable.stringtable.addString("EOF in comment"));
    case YYSTRING:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, AbstractTable.stringtable.addString("EOF in string constant"));
    }
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return new Symbol(TokenConstants.EQ); }
					case -3:
						break;
					case 3:
						{
    return new Symbol(TokenConstants.ERROR, AbstractTable.stringtable.addString(yytext())); 
}
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.MULT); }
					case -5:
						break;
					case 5:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.MINUS); }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.LT); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.COMMA); }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.DIV); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.PLUS); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.DOT); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.COLON); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.NEG); }
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.AT); }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.SEMI); }
					case -20:
						break;
					case 20:
						{
    curr_lineno += 1;    
}
					case -21:
						break;
					case 21:
						{
}
					case -22:
						break;
					case 22:
						{
    // string end as EOF
    yybegin(YYSTRING);
    String s = yytext();
    for (int i = 0; i < s.length(); ++i) {if (s.charAt(i) == '\n') {curr_lineno += 1;}}
}
					case -23:
						break;
					case 23:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -24:
						break;
					case 24:
						{
    return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
}
					case -25:
						break;
					case 25:
						{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
					case -26:
						break;
					case 26:
						{
    return new Symbol(TokenConstants.ERROR, AbstractTable.stringtable.addString("Unmatched *)"));
}
					case -27:
						break;
					case 27:
						{ return new Symbol(TokenConstants.IN); }
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.IF); }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.OF); }
					case -30:
						break;
					case 30:
						{
    String s = yytext();
    if (s.charAt(s.length() - 1) == '\n') {
        curr_lineno += 1;
    }
}
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.LE); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.FI); }
					case -34:
						break;
					case 34:
						{
    // trans to yycomment
    yybegin(YYCOMMENT);
    nested_comment += 1;
}
					case -35:
						break;
					case 35:
						{
    // end as \n
    String s = yytext();
    for (int i = 0; i < s.length(); ++i) {if (s.charAt(i) == '\n') {curr_lineno += 1;}}
    if (s.length() >= MAX_STR_CONST) {
        return new Symbol(TokenConstants.ERROR, AbstractTable.stringtable.addString("String constant too long")); 
    }
    return new Symbol(TokenConstants.ERROR, AbstractTable.stringtable.addString("Unterminated string constant"));
}
					case -36:
						break;
					case 36:
						{
    // end as "
    String s = yytext();
    for (int i = 0; i < s.length(); ++i) {if (s.charAt(i) == '\n') {curr_lineno += 1;}}
    //System.out.println(s);
    s = Utilities.reverseEscape(s);
    s = s.substring(1, s.length() - 1);
    if (s.length() >= MAX_STR_CONST) {
        return new Symbol(TokenConstants.ERROR, AbstractTable.stringtable.addString("String constant too long")); 
    }
    //s = s.replaceAll("\\\\\\n", "\n");
    //System.out.println(s);
    return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(s)); 
}
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.NEW); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.NOT); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.LET); }
					case -40:
						break;
					case 40:
						{
    // contains null charactor
    String s = yytext();
    for (int i = 0; i < s.length(); ++i) {if (s.charAt(i) == '\n') {curr_lineno += 1;}}
    return new Symbol(TokenConstants.ERROR, AbstractTable.stringtable.addString("String constant null character"));
}
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.ESAC); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.ELSE); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.THEN); }
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.POOL); }
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.LOOP); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.CASE); }
					case -47:
						break;
					case 47:
						{ 
    return new Symbol(TokenConstants.BOOL_CONST, new Boolean(true));
}
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.CLASS); }
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.WHILE); }
					case -50:
						break;
					case 50:
						{ 
    return new Symbol(TokenConstants.BOOL_CONST, new Boolean(false));
}
					case -51:
						break;
					case 51:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -52:
						break;
					case 52:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -53:
						break;
					case 53:
						{
}
					case -54:
						break;
					case 54:
						{
    nested_comment -= 1;
    if (nested_comment == 0) {
        yybegin(YYINITIAL);
    }
}
					case -55:
						break;
					case 55:
						{
    nested_comment += 1;
}
					case -56:
						break;
					case 57:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -57:
						break;
					case 58:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -58:
						break;
					case 59:
						{ return new Symbol(TokenConstants.IN); }
					case -59:
						break;
					case 60:
						{ return new Symbol(TokenConstants.IF); }
					case -60:
						break;
					case 61:
						{ return new Symbol(TokenConstants.OF); }
					case -61:
						break;
					case 62:
						{
    String s = yytext();
    if (s.charAt(s.length() - 1) == '\n') {
        curr_lineno += 1;
    }
}
					case -62:
						break;
					case 63:
						{ return new Symbol(TokenConstants.FI); }
					case -63:
						break;
					case 64:
						{ return new Symbol(TokenConstants.NEW); }
					case -64:
						break;
					case 65:
						{ return new Symbol(TokenConstants.NOT); }
					case -65:
						break;
					case 66:
						{ return new Symbol(TokenConstants.LET); }
					case -66:
						break;
					case 67:
						{ return new Symbol(TokenConstants.ESAC); }
					case -67:
						break;
					case 68:
						{ return new Symbol(TokenConstants.ELSE); }
					case -68:
						break;
					case 69:
						{ return new Symbol(TokenConstants.THEN); }
					case -69:
						break;
					case 70:
						{ return new Symbol(TokenConstants.POOL); }
					case -70:
						break;
					case 71:
						{ return new Symbol(TokenConstants.LOOP); }
					case -71:
						break;
					case 72:
						{ return new Symbol(TokenConstants.CASE); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.CLASS); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.WHILE); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -76:
						break;
					case 77:
						{
}
					case -77:
						break;
					case 79:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -78:
						break;
					case 80:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -79:
						break;
					case 81:
						{
}
					case -80:
						break;
					case 83:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -81:
						break;
					case 84:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -82:
						break;
					case 86:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -83:
						break;
					case 87:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -84:
						break;
					case 89:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -85:
						break;
					case 90:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -86:
						break;
					case 91:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -87:
						break;
					case 92:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -88:
						break;
					case 93:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -89:
						break;
					case 94:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -90:
						break;
					case 95:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -91:
						break;
					case 96:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -92:
						break;
					case 97:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -93:
						break;
					case 98:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -94:
						break;
					case 99:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -95:
						break;
					case 100:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -96:
						break;
					case 101:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -97:
						break;
					case 102:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -98:
						break;
					case 103:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -99:
						break;
					case 104:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -100:
						break;
					case 105:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -101:
						break;
					case 106:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -102:
						break;
					case 107:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -103:
						break;
					case 108:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -104:
						break;
					case 109:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -105:
						break;
					case 110:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -106:
						break;
					case 111:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -107:
						break;
					case 112:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -108:
						break;
					case 113:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -109:
						break;
					case 114:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -110:
						break;
					case 115:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -111:
						break;
					case 116:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -112:
						break;
					case 117:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -113:
						break;
					case 118:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -114:
						break;
					case 119:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -115:
						break;
					case 120:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -116:
						break;
					case 121:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -117:
						break;
					case 122:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -118:
						break;
					case 123:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -119:
						break;
					case 124:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -120:
						break;
					case 125:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -121:
						break;
					case 126:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -122:
						break;
					case 127:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -123:
						break;
					case 128:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -124:
						break;
					case 129:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -125:
						break;
					case 130:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -126:
						break;
					case 131:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -127:
						break;
					case 132:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -128:
						break;
					case 133:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -129:
						break;
					case 134:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -130:
						break;
					case 135:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -131:
						break;
					case 136:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -132:
						break;
					case 137:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -133:
						break;
					case 138:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -134:
						break;
					case 139:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -135:
						break;
					case 140:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -136:
						break;
					case 141:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -137:
						break;
					case 142:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -138:
						break;
					case 143:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -139:
						break;
					case 144:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -140:
						break;
					case 145:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -141:
						break;
					case 146:
						{
    // typeID
    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -142:
						break;
					case 147:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -143:
						break;
					case 148:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -144:
						break;
					case 149:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -145:
						break;
					case 150:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -146:
						break;
					case 151:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -147:
						break;
					case 152:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -148:
						break;
					case 153:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -149:
						break;
					case 154:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -150:
						break;
					case 155:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -151:
						break;
					case 156:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -152:
						break;
					case 157:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -153:
						break;
					case 158:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -154:
						break;
					case 159:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -155:
						break;
					case 160:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -156:
						break;
					case 161:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -157:
						break;
					case 162:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -158:
						break;
					case 163:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -159:
						break;
					case 164:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -160:
						break;
					case 165:
						{
    // objectID
    AbstractSymbol sym = AbstractTable.idtable.addString(yytext());
    return new Symbol(TokenConstants.OBJECTID, sym);
}
					case -161:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
