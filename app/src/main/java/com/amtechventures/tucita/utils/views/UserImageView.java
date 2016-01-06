package com.amtechventures.tucita.utils.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.amtechventures.tucita.model.context.user.UserContext;
import com.amtechventures.tucita.model.domain.user.User;

public class UserImageView extends Drawable {

    private Paint paint = new Paint();
    private final int POSITION_ZERO = 0;
    private final int MAX_WIDTH = 50;
    private final int HEIGHT = 90;
    private int ALPHA_TRANSPARENCY = 125;
    private final int TEXT_SIZE = 25;
    private final int TEXT_X = 105;
    private final int TEXT_Y = 60;
    private final int PICTURE_Y = 20;
    private final int PICTURE_X = 20;

    private Bitmap picture;
    private UserContext userContext = new UserContext();
    private Typeface typeface;

    public UserImageView(Bitmap picture, Typeface typeface){

        this.picture = picture;

        this.typeface = typeface;
    }

    @Override
    public void draw(Canvas canvas) {

        paint.setColor(Color.GRAY);

        //paint.setAlpha(ALPHA_TRANSPARENCY);

        if(picture != null) {

            picture = Bitmap.createScaledBitmap(picture, 60, 60, false);

            canvas.drawBitmap(picture, PICTURE_X, PICTURE_Y, paint);
        }

        User user = userContext.currentUser();

        paint.setFakeBoldText(true);

        paint.setTypeface(typeface);

        paint.setTextSize(TEXT_SIZE);

        if(user != null) {

            String name = user.getName();

            if (name != null) {

                canvas.drawText(name, TEXT_X, TEXT_Y, paint);
            }
        }


    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {

        return 0;

    }

}
