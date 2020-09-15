package ru.kamaz.itis.timer.gallery.constants;

public class ConstantUtils {

  public static final String LOG_TAG = "itisLog";

  // region НАШИ ФРАГМЕНТЫ
  public static final String MEDIA_PHOTO_FRAGMENT = "MEDIA_PHOTO_FRAGMENT";
  public static final String MEDIA_DEVICE_FRAGMENT = "MEDIA_DEVICE_FRAGMENT";
  public static final String PHONE_PAGER_FRAGMENT = "PHONE_PAGER_FRAGMENT";
  public static final String VIDEO_LIST_FRAGMENT = "VIDEO_LIST_FRAGMENT";
  public static final String RADIO_FRAGMENT = "RadioFragment";
  public static final String AUDIO_LIST_FRAGMENT = "AUDIO_LIST_FRAGMENT";
  public static final String SETTINGS_FRAGMENT = "SETTINGS_FRAGMENT";
  public static final String APPS_MENU_FRAGMENT = "APPS_MENU_FRAGMENT";
  public static final String AUX_FRAGMENT = "AuxFragment";
  public static final String PHONE_SETTINGS_FRAGMENT = "PHONE_SETTINGS_FRAGMENT";
  public static final String NOTOFICATION_FRAGMENT = "NOTOFICATION_FRAGMENT";
  public static final String EQUALIZER_FRAGMENT = "EQUALIZER_FRAGMENT";
  public static final String MAIN_MENU_FRAGMENT = "MAIN_MENU_FRAGMENT";
  public static final String MAIN_2_MENU_FRAGMENT = "MAIN_2_MENU_FRAGMENT";
  public static final String AUDIO_SYSTEM_SETTINGS_FRAGMENT = "AUDIO_SYSTEM_SETTINGS_FRAGMENT";
  public static final String PZHD_FRAGMENT = "PZHD_FRAGMENT";
  public static final String PZHD_START_TIMER_FRAGMENT = "PZHD_START_TIMER_FRAGMENT";
  public static final String PZHD_START_TIMER_EDITOR_FRAGMENT = "PZHD_START_TIMER_EDITOR_FRAGMENT";
  public static final String VOICE_SETTINGS_FRAGMENT = "VOICE_SETTINGS_FRAGMENT";
  public static final String MEDIA_PLAYER_FRAGMENT = "MEDIA_PLAYER_FRAGMENT";
  public static final String MEDIA_PHOTO_PAGER_FRAGMENT = "MEDIA_PHOTO_PAGER_FRAGMENET";
  public static final String BOTTOM_LEFT_MENU_PAGER_FRAGMENT = "BOTTOM_LEFT_MENU_PAGER_FRAGMENT";
  public static final String BOTTOM_MEDIA_PLAYER_FRAGMENT = "BottomMediaPlayerFragment";
  public static final String WIFI_FRAGMENGT = "WIFI_FRAGMENT";
  public static final String DATE_TIME_SETTINGS_FRAGMENT = "DATE_TIME_FRAGMENT";
  public static final String DISPLAY_SETTINGS_FRAGMENT = "DISPLAY_SETTINGS_FRAGMENT";
  public static final String STORE_SETTINGS_FRAGMENT = "STORE_SETTINGS_FRAGMENT";
  public static final String LANGUAGE_SETTINGS_FRAGMENT = "LANGUAGE_SETTINGS_FRAGMENT";
  public static final String UPDATE_SETTINGS_FRAGMENT = "UPDATE_SETTINGS_FRAGMENT";
  public static final String TIMEZONE_SETTINGS_FRAGMENT = "TIMEZONE_SETTINGS_FRAGMENT";
  public static final String MEDIA_PHOTO_ALBUMS_FRAGMENT = "MEDIA_PHOTO_ALBUMS_FRAGMENT";
  public static final String DIAGNOSTIC_FRAGMENT = "DIAGNOSTIC_FRAGMENT";

  public static final String ULTRA_BASS_FRAGMENT = "ULTRA_BASS_FRAGMENT";
  public static final String AUDIO_BALANCE_FRAGMENT = "AUDIO_BALANCE_FRAGMENT";
  // endregion

  public static final String APP_PREFERENCES = "app_settings";

  public static final String PREFERENCES_MEDIA_SOURCE_ID = "media_source_id";
  public static final String PREFERENCES_BT_NAME = "bluetooth_name";
  public static final String BUNDLE_VIEW_MODE = "bvm";

  //region Socket
  public static final String GET_MATCHING_TABLE = "matchingtable";
  public static final String CLOSED_CONNECTION = "stopdata";
  public static final int SERVER_PORT = 5544;
  public static final String START_DATA = "startdata";
  public static final String TRACKER_END_POINT = "192.168.225.1";
  //endregion

  // region actions
  public static final String ACTION_AUX_F = "com.coagent.intent.action.F_AUX";
  public static final String ACTION_AUX = "com.coagent.intent.action.AUX";
  public static final String ACTION_SOURCE_CHANGE = "com.coagent.intent.action.SOURCE_CHANGED";
  public static final String ACTION_WEATHER_CHANGE = "ru.bis.itis.presentation.weather_update";
  public static final String ACTION_SYSTEM_UI_USER_CLICK = "ru.bis.itis.presentation.intent.action.user";
  public static final String ACTION_USER_CHANGE = "ru.bis.itis.presentation.action.changeduser";
  public static final String ACTION_VOICE_COMMAND = "ru.bis.itis.presentation.intent.action.voicecommand";
  public static final String ACTION_START_FRAGMENT_COMMAND = "ru.bis.itis.presentation.intent.action.startfragment";
  public static final String ACTION_CLEAR_FRAGMENT_STACK_COMMAND = "ru.bis.itis.presentation.intent.action.startclearfragment";
  public static final String USB_PATH = "/storage/udisk/";
  public static final String SD_PATH = "/storage/extsd/";
  public static final String USB_PATH_2 = "/storage/udisk3/";
  public static final String ACTION_NOTIFICATION_MESSAGE = "ru.bis.itis.presentation.action.notificaton.message";
  public static final String ACTION_UPDATE_APP_LIST = "ru.bis.itis.presentation.action.update_action_list";
  public static final String MIME_TYPE_JSON_AUTH = "application/json.auth";
  // endregion

  // region MediaPlayer
  public static final String PREF_MEDIA_PLAYER_SETTINGS = "mediaplayersettings";
  public static final String PREFERENCES_REPEAT_MODE = "prm";
  public static final String PREFERENCES_RANDOM_MODE = "prnm";
  public static final String PREFERENCES_PLAY_LIST_PATH = "pplp";
  public static final String PREFERENCES_PLAY_IS_PLAYING = "ppip";
  public static final String PREFERENCES_PLAY_LIST_POSITION_IN_LIST = "pplpil";
  public static final String PREFERENCES_PLAY_LIST_POSITION_IN_TRACK = "pplpit";
  public static final String PREFERENCES_PLAY_LIST_HASH_CODE = "pplhc";
  public static final String PREFERENCES_RADIO_CURRENT_FM_STATION_FREQ = "stationCurFmFreq";
  // endregion

  // region events
  public static final String EVENT_AUDIO_FOCUS_LOSS = "AUDIOFOCUS_LOSS";
  public static final String EVENT_AUDIOFOCUS_LOSS_TRANSIENT = "AUDIOFOCUS_LOSS_TRANSIENT";
  public static final String EVENT_AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK";
  public static final String EVENT_AUDIOFOCUS_GAIN = "AUDIOFOCUS_GAIN";
  public static final String BUNDLE_VOICE_COMMAND = "bvcmd";
  public static final String BUNDLE_START_FRAGMENT_CLASS_NAME = "bsfcn";
  // endregion

  // region BUNDLEs
  public static final int DELETE_APP_REQUEST_CODE = 110;
  // endregion

  // region VOICE_COMMANDS
  public static final String RADIO_VOICE_COMMAND = "Радио";
  public static final String CANAL_VOICE_COMMAND = "Канал";
  public static final String STATION_VOICE_COMMAND = "Станция";
  public static final String PLAYER_VOICE_COMMAND = "Плеер";
  public static final String MEDIAPLAYER_VOICE_COMMAND = "Медиаплеер";
  public static final String MUSIC_VOICE_COMMAND = "Музыка";
  public static final String MENU_VOICE_COMMAND = "Меню";
  public static final String MAINMENU_VOICE_COMMAND = "Главное меню";
  public static final String VOICE_VOICE_COMMAND = "Звук";
  public static final String PLAYLIST_VOICE_COMMAND = "Список воспроизведения";
  public static final String MIX_VOICE_COMMAND = "Перемешать";
  public static final String MIX_MUSIC_VOICE_COMMAND = "Перемешать музыку";
  public static final String MIX_SONG_VOICE_COMMAND = "Перемешать песни";
  // endregion

  public static final int DEFAULT_RADIO_STATION = 8750;
  public static final int NUMSTATION_FM = 206;
  public static final String BUNDLE_WEATHER_CHANGE_MODE = "bwcm";
  public static final long MIN_CLIMATIC_LEVEL = 16;
  public static final long MAX_CLIMATIC_LEVEL = 30;

  public static final String BUNDLE_MEDIA_FILE_PATH = "bmfp";
  public static final String BUNDLE_ALBUM_ID = "baid";
  public static final String BUNDLE_PHOTO_ID = "bpid";
  public static final String BUNDLE_PHOTOS_LIST = "bpl";
  public static final String BUNDLE_LIST_SELECT_POSITION = "blsp";
  public static final String BUNDLE_PHOTO_PATH = "bpp";
  public static final String PREFERENCES_DIRECTORY = "pref_directory";

  //Notifications
  public static final int NOTIFICATION_ICON_SIZE = 70;

  //Calendar
  public static final String ALERT_ITEMS_BLOCK = "alert_items_block";
  public static final String EMPTY_EVENT_LIST_BLOCK = "empty_event_list_block";
  public static final String EVENTS_LIST_CONST = "events_list";
  public static final String ADD_NEW_EVENT_BLOCK_CONST = "add_new_event_block";
  public static final String EDIT_EVENT_BLOCK_CONST = "edit_event_block";
  public static final String SINGLE_EVENT_BLOCK_CONST = "single_event_block_const";
}
