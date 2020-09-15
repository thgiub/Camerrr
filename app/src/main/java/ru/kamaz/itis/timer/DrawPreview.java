package ru.kamaz.itis.timer;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DrawPreview {
    private static final String TAG = "DrawPreview";

    private final MyApplicationInterface applicationInterface;
    private boolean show_time_pref;
    private boolean show_free_memory_pref;
    private boolean show_iso_pref;
    private boolean show_video_max_amp_pref;
    private boolean show_zoom_pref;
    private boolean show_battery_pref;
    private boolean show_angle_pref;
    private int angle_highlight_color_pref;
    private boolean show_geo_direction_pref;
    private boolean take_photo_border_pref;
    private boolean preview_size_wysiwyg_pref;
    private boolean store_location_pref;
    private boolean show_angle_line_pref;
    private boolean show_pitch_lines_pref;
    private boolean show_geo_direction_lines_pref;
    private boolean immersive_mode_everything_pref;
    private boolean has_stamp_pref;
    private boolean is_raw_pref; // whether in RAW+JPEG or RAW only mode
    private boolean is_raw_only_pref; // whether in RAW only mode
    private boolean is_face_detection_pref;
    private boolean is_audio_enabled_pref;
    private boolean is_high_speed;
    private float capture_rate_factor;
    private boolean auto_stabilise_pref;
    private String preference_grid_pref;
    private String ghost_image_pref;
    private String ghost_selected_image_pref = "";
    private Bitmap ghost_selected_image_bitmap;
    private boolean want_histogram;

    private boolean want_zebra_stripes;
    private int zebra_stripes_threshold;
    private boolean want_focus_peaking;
    private int focus_peaking_color_pref;

    // avoid doing things that allocate memory every frame!
    private final Paint p = new Paint();
    private final RectF draw_rect = new RectF();
    private final int [] gui_location = new int[2];
    private final static DecimalFormat decimalFormat = new DecimalFormat("#0.0");

    private Calendar calendar;
    private final DateFormat dateFormatTimeInstance = DateFormat.getTimeInstance();

    private final int [] temp_histogram_channel = new int[256];
    // cached Rects for drawTextWithBackground() calls
    private Rect text_bounds_time;
    private Rect text_bounds_free_memory;
    private Rect text_bounds_angle_single;
    private Rect text_bounds_angle_double;

    private final static double close_level_angle = 1.0f;
    private String angle_string; // cached for UI performance
    private double cached_angle; // the angle that we used for the cached angle_string
    private long last_angle_string_time;

    private float free_memory_gb = -1.0f;
    private String free_memory_gb_string;
    private long last_free_memory_time;

    private String current_time_string;
    private long last_current_time_time;

    private String iso_exposure_string;
    private boolean is_scanning;
    private long last_iso_exposure_time;

    private boolean need_flash_indicator = false;
    private long last_need_flash_indicator_time;

    private final IntentFilter battery_ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    private boolean has_battery_frac;
    private float battery_frac;
    private long last_battery_time;

    private boolean has_video_max_amp;
    private int video_max_amp;
    private long last_video_max_amp_time;
    private int video_max_amp_prev2;
    private int video_max_amp_peak;

    private Bitmap location_bitmap;
    private Bitmap location_off_bitmap;
    private Bitmap raw_jpeg_bitmap;
    private Bitmap raw_only_bitmap;
    private Bitmap auto_stabilise_bitmap;
    private Bitmap dro_bitmap;
    private Bitmap hdr_bitmap;
    private Bitmap panorama_bitmap;
    private Bitmap expo_bitmap;
    private Bitmap focus_bracket_bitmap;
    private Bitmap burst_bitmap;
    private Bitmap nr_bitmap;
    private Bitmap photostamp_bitmap;
    private Bitmap flash_bitmap;
    private Bitmap face_detection_bitmap;
    private Bitmap audio_disabled_bitmap;
    private Bitmap high_speed_fps_bitmap;
    private Bitmap slow_motion_bitmap;
    private Bitmap time_lapse_bitmap;
    private Bitmap rotate_left_bitmap;
    private Bitmap rotate_right_bitmap;

    private final Rect icon_dest = new Rect();
    private long needs_flash_time = -1; // time when flash symbol comes on (used for fade-in effect)
    private final Path path = new Path();

    private Bitmap last_thumbnail; // thumbnail of last picture taken
    private volatile boolean thumbnail_anim; // whether we are displaying the thumbnail animation; must be volatile for test project reading the state
    private long thumbnail_anim_start_ms = -1; // time that the thumbnail animation started
    private final RectF thumbnail_anim_src_rect = new RectF();
    private final RectF thumbnail_anim_dst_rect = new RectF();
    private final Matrix thumbnail_anim_matrix = new Matrix();
    private boolean last_thumbnail_is_video; // whether thumbnail is for video

    private boolean show_last_image; // whether to show the last image as part of "pause preview"
    private final RectF last_image_src_rect = new RectF();
    private final RectF last_image_dst_rect = new RectF();
    private final Matrix last_image_matrix = new Matrix();
    private boolean allow_ghost_last_image; // whether to allow ghosting the last image

    private long ae_started_scanning_ms = -1; // time when ae started scanning

    private boolean taking_picture; // true iff camera is in process of capturing a picture (including any necessary prior steps such as autofocus, flash/precapture)
    private boolean capture_started; // true iff the camera is capturing
    private boolean front_screen_flash; // true iff the front screen display should maximise to simulate flash
    private boolean image_queue_full; // whether we can no longer take new photos due to image queue being full (or rather, would become full if a new photo taken)

    private boolean continuous_focus_moving;
    private long continuous_focus_moving_ms;

    private boolean enable_gyro_target_spot;
    private final List<float []> gyro_directions = new ArrayList<>();
    private final float [] transformed_gyro_direction = new float[3];
    private final float [] gyro_direction_up = new float[3];
    private final float [] transformed_gyro_direction_up = new float[3];

    // call updateCachedViewAngles() before reading these values
    private float view_angle_x_preview;
    private float view_angle_y_preview;
    private long last_view_angles_time;

    // OSD extra lines
    private String OSDLine1;
    private String OSDLine2;

    public DrawPreview(MainActivity main_activity, MyApplicationInterface applicationInterface) {
        this.applicationInterface = applicationInterface;
    }

    public void updateThumbnail(Bitmap thumbnail, boolean is_video, boolean want_thumbnail_animation) {
        if( MyDebug.LOG )
            Log.d(TAG, "updateThumbnail");
        if( want_thumbnail_animation && applicationInterface.getThumbnailAnimationPref() ) {
            if( MyDebug.LOG )
                Log.d(TAG, "thumbnail_anim started");
            thumbnail_anim = true;
            thumbnail_anim_start_ms = System.currentTimeMillis();
        }
        Bitmap old_thumbnail = this.last_thumbnail;
        this.last_thumbnail = thumbnail;
        this.last_thumbnail_is_video = is_video;
        this.allow_ghost_last_image = true;
        if( old_thumbnail != null ) {
            // only recycle after we've set the new thumbnail
            old_thumbnail.recycle();
        }
    }
}
