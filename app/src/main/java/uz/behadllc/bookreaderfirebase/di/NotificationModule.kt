package uz.behadllc.bookreaderfirebase.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import uz.behadllc.bookreaderfirebase.ui.MainActivity
import uz.behadllc.bookreaderfirebase.utils.Constants.CHANNEL_ID
import uz.behadllc.bookreaderfirebase.utils.Constants.PENDING_CODE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import uz.behadllc.bookreaderfirebase.R

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {
    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder = NotificationCompat.Builder(
        context,
        CHANNEL_ID
    ).setSmallIcon(R.drawable.ic_download)
        .setAutoCancel(false)
        .setOngoing(false)
        .setContentTitle("Book Name")
        .setContentIntent(pendingIntent)

    @ServiceScoped
    @Provides
    fun providePendingIntent(
        @ApplicationContext context: Context,
        intent: Intent
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            PENDING_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    @ServiceScoped
    @Provides
    fun provideIntent(
        @ApplicationContext context: Context
    ): Intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }
}