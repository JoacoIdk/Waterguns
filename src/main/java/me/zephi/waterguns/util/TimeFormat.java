package me.zephi.waterguns.util;

import me.zephi.waterguns.util.MathUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeFormat {
    private static final Map<Character, Long> timeUnits = new ConcurrentHashMap<>();
    private static final Map<Integer, Character> timeUnitCharacters = new ConcurrentHashMap<>();
    private static final Pattern timePattern = Pattern.compile("(\\d+y)?(\\d+M)?(\\d+w)?(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?(\\d+S)?");
    private static final Pattern formatPattern = Pattern.compile("(\\([^()]*_y+[^()]*-?\\+?~?[0-9]*\\))?(\\([^()]*_M+[^()]*-?\\+?~?[0-9]*\\))?(\\([^()]*_w+[^()]*-?\\+?~?[0-9]*\\))?(\\([^()]*_d+[^()]*-?\\+?~?[0-9]*\\))?(\\([^()]*_h+[^()]*-?\\+?~?[0-9]*\\))?(\\([^()]*_m+[^()]*-?\\+?~?[0-9]*\\))?(\\([^()]*_s+[^()]*-?\\+?~?[0-9]*\\))?(\\([^()]*_S+[^()]*-?\\+?~?[0-9]*\\))?");
    private static final Pattern formatCharacterPattern = Pattern.compile("_[yMwdhmsS]+");

    static {
        timeUnits.put( 'y', (long) 31536e9 );
        timeUnits.put( 'M', (long) 25922e9 );
        timeUnits.put( 'w', (long) 6048e8  );
        timeUnits.put( 'd', (long) 864e8   );
        timeUnits.put( 'h', (long) 36e8    );
        timeUnits.put( 'm', 60000L         );
        timeUnits.put( 's', 1000L          );
        timeUnits.put( 'S', 1L             );

        timeUnitCharacters.put( 1, 'y' );
        timeUnitCharacters.put( 2, 'M' );
        timeUnitCharacters.put( 3, 'w' );
        timeUnitCharacters.put( 4, 'd' );
        timeUnitCharacters.put( 5, 'h' );
        timeUnitCharacters.put( 6, 'm' );
        timeUnitCharacters.put( 7, 's' );
        timeUnitCharacters.put( 8, 'S' );
    }


    public static String formatMillisToTime(long time, String format) {
        long remaining = time;

        Matcher matcher = formatPattern.matcher(format);
        StringBuilder builder = new StringBuilder();

        while (matcher.find()) {
            if (matcher.group() == null || matcher.group().isEmpty())
                continue;

            for (int i : MathUtil.range(1, matcher.groupCount())) {
                String found = matcher.group(i);

                if (found == null || found.isEmpty())
                    continue;

                found = found.substring(1, found.length() - 1);

                boolean addZeros = found.matches(".*~[0-9]*$");
                int len = -1;

                if (addZeros) {
                    String number = found.substring(found.lastIndexOf('~') + 1);
                    String content = found.replace(String.format("~%s", number), "");

                    if (number.isEmpty())
                        content = found.substring(0, found.length() - 1);
                    else
                        len = Integer.parseInt(number);

                    found = content;
                }

                boolean addIfZero = found.endsWith("+");

                if (addIfZero)
                    found = found.substring(0, found.length() - 1);

                boolean allowMoreDigits = !found.endsWith("-");

                if (!allowMoreDigits)
                    found = found.substring(0, found.length() - 1);


                Matcher replaceMatcher = formatCharacterPattern.matcher(found);

                if (!replaceMatcher.find())
                    continue;

                char measurementCharacter = timeUnitCharacters.get(i);
                long measurementMillis = timeUnits.get(measurementCharacter);

                long toAdd = (long) ((double) remaining / measurementMillis);

                String replace = replaceMatcher.group();
                String value = String.valueOf(toAdd);

                if (len == -1)
                    len = replace.length();

                if (toAdd < 1 && !addIfZero)
                    continue;

                remaining -= toAdd * measurementMillis;

                if (addZeros) {
                    for (int j = 0; j < len - value.length() - 1; j++)
                        value = '0' + value;
                }

                if (!allowMoreDigits && value.length() > replace.length() - 1)
                    value = value.substring(0, replace.length() - 1);

                builder.append(found.replace(replace, value));
            }
        }

        return builder.length() == 0 ? "0" : builder.toString();
    }

    public static String formatMillisToHMS(long time) {
        return formatMillisToTime(time, "(_hh:-+~)(_mm:-+~)(_ss-+~)(._SSS-)");
    }

    public static String formatMillisToMSM(long time) {
        return formatMillisToTime(time, "(_mm:-~)(_ss-+~)(._S-~3)");
    }

    public static String formatMillisToNiceTime(long time) {
        return formatMillisToTime(time, "(_y years )(_w weeks )(_M months )(_d days )(_h hours )(_m minutes )(_s seconds)( _S milliseconds)");
    }

    public static long formatTimeToMillis(String time) {
        try {
            if(time == null || time.isEmpty())
                return -1;

            Matcher matcher = timePattern.matcher(time);
            long millis = 0;

            while (matcher.find()) {
                if (matcher.group() == null || matcher.group().isEmpty())
                    continue;

                for (int i : MathUtil.range(1, matcher.groupCount())) {
                    String found = matcher.group(i);

                    if (found == null || found.isEmpty())
                        continue;

                    long value = Long.parseLong(found.replaceAll("[a-zA-Z]", ""));

                    millis += value * timeUnits.get(timeUnitCharacters.get(i));
                }
            }

            return millis;
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
