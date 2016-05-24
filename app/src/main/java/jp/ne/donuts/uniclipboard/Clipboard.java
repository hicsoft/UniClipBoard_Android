/**
 * Created by takanobu on 16/05/24.
 */
package jp.ne.donuts.uniclipboard;

        import android.app.Activity;
        import android.content.ClipData;
        import android.content.ClipData.Item;
        import android.content.ClipDescription;
        import android.content.ClipboardManager;
        import android.content.Context;

        import com.unity3d.player.UnityPlayer;
        import java.util.concurrent.Callable;
        import java.util.concurrent.FutureTask;

public class Clipboard
{
    public static void setText(final String text)
    {
        final Activity activity = UnityPlayer.currentActivity;
        activity.runOnUiThread(new Runnable()
        {
            public void run() {
                ClipData.Item item = new ClipData.Item(text);
                String[] mimeType = new String[1];
                //mimeType[0] = "text/uri-list";
                mimeType[0] = ClipDescription.MIMETYPE_TEXT_PLAIN;
                ClipData cd = new ClipData(new ClipDescription("text_data", mimeType), item);
                ClipboardManager cm = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(cd);
            }
        });
    }

    public static String getText() {
        final Activity activity = UnityPlayer.currentActivity;
        FutureTask futureResult = new FutureTask(new Callable()
        {
            public String call() throws Exception {
                ClipboardManager cm = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = cm.getPrimaryClip();
                if (cd != null) {
                    return cd.getItemAt(0).getText().toString();
                }
                return "";
            }
        });
        activity.runOnUiThread(futureResult);
        try
        {
            return (String)futureResult.get(); } catch (Exception e) {
        }
        return "";
    }
}