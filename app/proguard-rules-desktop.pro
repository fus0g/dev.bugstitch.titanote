# specific rule for your database
-keep class dev.bugstitch.titanote.data.room.NotesDatabase_Impl { *; }

# generic rules for Room (good practice)
-keep class * extends androidx.room.RoomDatabase { *; }
-keep class * extends androidx.room.RoomDatabase$Builder { *; }
-keep class * extends androidx.room.migration.AutoMigrationSpec { *; }

# If you use SQLite directly
-keep class androidx.sqlite.** { *; }