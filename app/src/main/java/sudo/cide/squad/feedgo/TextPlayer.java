package sudo.cide.squad.feedgo;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Circle;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Rotate3D;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Direction;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

class TextPlayer {

    static void play(TextSurface textSurface, AssetManager assetManager) {

        Typeface fromAsset = Typeface.createFromAsset(assetManager, "fonts/simplicity.ttf");
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(fromAsset);

        Text textIn = TextBuilder
                .create("welcome to")
                .setPaint(paint)
                .setSize(30)
                .setAlpha(0)
                .setColor(Color.rgb(150, 206, 180))
                .setPosition(Align.SURFACE_CENTER).build();

        Text textBiggest = TextBuilder
                .create("FeedGO")
                .setPaint(paint)
                .setSize(70)
                .setAlpha(0)
                .setColor(Color.rgb(255, 111, 105))
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textIn).build();

        Text textProblem = TextBuilder
                .create("a location aware ecosystem")
                .setPaint(paint)
                .setSize(35)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textBiggest).build();

        Text textWeCreated = TextBuilder
                .create("where you can")
                .setPaint(paint)
                .setSize(30)
                .setAlpha(0)
                .setColor(Color.rgb(255, 238, 173))
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textProblem).build();

        Text textTitle = TextBuilder
                .create("search & report")
                .setPaint(paint)
                .setSize(50)
                .setAlpha(0)
                .setColor(Color.rgb(255, 204, 92))
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textWeCreated).build();

        Text textApplication = TextBuilder
                .create("all sorts of things")
                .setPaint(paint)
                .setSize(30)
                .setAlpha(0)
                .setColor(Color.rgb(136, 216, 176))
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textTitle).build();

        Text textTrack = TextBuilder
                .create("and have a chance to")
                .setPaint(paint)
                .setSize(25)
                .setAlpha(0)
                .setColor(Color.rgb(150, 206, 180))
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textApplication).build();

        Text textAQ = TextBuilder
                .create("earn")
                .setPaint(paint)
                .setSize(50)
                .setAlpha(0)
                .setColor(Color.rgb(255, 238, 173))
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textTrack).build();

        Text textBetter = TextBuilder
                .create("with each contribution you make.")
                .setPaint(paint)
                .setSize(30)
                .setAlpha(0)
                .setColor(Color.rgb(255, 111, 105))
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textAQ).build();

        textSurface.play(
                new Sequential(
                        ShapeReveal.create(textIn, 750, SideCut.show(Side.LEFT), false),
                        new Parallel(new TransSurface(500, textBiggest, Pivot.CENTER), ShapeReveal.create(textBiggest, 1300, SideCut.show(Side.LEFT), false)),
                        Delay.duration(800),
                        new Parallel(new TransSurface(750, textProblem, Pivot.CENTER), Slide.showFrom(Side.LEFT, textProblem, 750), ChangeColor.to(textProblem, 750, Color.WHITE)),
                        Delay.duration(800),
                        new Parallel(TransSurface.toCenter(textWeCreated, 500), Slide.showFrom(Side.TOP, textWeCreated, 500)),
                        new Parallel(TransSurface.toCenter(textTitle, 500), Rotate3D.showFromSide(textTitle, 750, Pivot.TOP)),
                        new Parallel(TransSurface.toCenter(textApplication, 750), Slide.showFrom(Side.TOP, textApplication, 500)),
                        Delay.duration(800),
                        new Parallel(
                                new TransSurface(700, textBetter, Pivot.CENTER),
                                new Sequential(
                                        new Sequential(ShapeReveal.create(textTrack, 500, Circle.show(Side.CENTER, Direction.OUT), false)),
                                        new Sequential(ShapeReveal.create(textAQ, 500, Circle.show(Side.CENTER, Direction.OUT), false)),
                                        new Sequential(ShapeReveal.create(textBetter, 500, Circle.show(Side.CENTER, Direction.OUT), false)),
                                        new TransSurface(1500, textTitle, Pivot.CENTER)
                                )
                        )

                )
        );
    }

}
