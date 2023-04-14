package net.devious.plugins.clockworks.framework;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BotMemory
{
    private static Map<String, Boolean> BotBoolMemory = new HashMap<>();
    private static Map<String, Integer> BotIntMemory = new HashMap<>();
    private static Map<String, String> BotStringMemory = new HashMap<>();


    public static void wipeMemory()
    {
        BotBoolMemory.clear();
        BotIntMemory.clear();
        BotStringMemory.clear();
    }

    public static int getInt(String key)
    {
        System.out.println("Retrieving int key: " + key);

        if (BotIntMemory.containsKey(key))
        {
            return BotIntMemory.get(key);
        }

        return -1;
    }

    public static boolean getBool(String key)
    {
        // System.out.println("Retrieving bool key: " + key);

        if (BotBoolMemory.containsKey(key))
        {
            return BotBoolMemory.get(key);
        }

        return false;
    }

    public static String getString(String key)
    {
        System.out.println("Retrieving string key: " + key);

        if (BotStringMemory.containsKey(key))
        {
            return BotStringMemory.get(key);
        }

        return "";
    }

    public static void setInt(String key, int value)
    {
        System.out.println("Set int key: " + key + " to value " + value);

        if (!BotIntMemory.containsKey(key))
        {
            BotIntMemory.put(key, value);
        }
        else
        {
            BotIntMemory.replace(key, value);
        }
    }

    public static void setBool(String key, boolean value)
    {
        System.out.println("Set bool key: " + key + " to value " + value);

        if (!BotBoolMemory.containsKey(key))
        {
            BotBoolMemory.put(key, value);
        }
        else
        {
            BotBoolMemory.replace(key, value);
        }
    }

    public static void setString(String key, String value)
    {
        System.out.println("Set string key: " + key + " to value " + value);

        if (!BotStringMemory.containsKey(key))
        {
            BotStringMemory.put(key, value);
        }
        else
        {
            BotStringMemory.replace(key, value);
        }
    }

}
