# FTC_IDK_VISION_LIB

<i>Simple and Easy to Use FTC Vision Pipeline System Library, Created By Team #4653 Irrational DoorKeepers</i>

## Explanation:

This library is a library that we have derived from Yamuna Software Solutions's, a technology non-profit run by some of our team members, PackTrack
application and computer vision library. The library includes all of your possible computer vision needs including color sampling, motion tracking,
object detection, and blob detection.

## Installation:

1. Open FTC project in <i>Android Studio</i> (no OnBot Java :[ )... <b>Gradle Scripts -> TeamCode-build.gradle -> add the lines</b>
   ```
   allprojects {
     repositories {
        maven { url 'https://jitpack.io' }
     }
   }

   dependencies {
     implementation 'com.github.FTCIrrationalDoorkeepers4653:FTC_IDK_VISION_LIB:Tag'
   }
   ```
   <b>-> Sync Gradle</b>
2. If <i>Jitpack</i> doesn't work, download this repo, extract, and go into <i>Android Studio</i>, add the repo as a module, go to <b>Gradle Scripts -> TeamCode-buld.release.gradle -> add the line</b> ```implementation project (':FTC_IDK_VISION_LIB')``` <b>-> Sync Gradle</b>
3. Lastly, add Source Files of <i>TeamCode-Files</i> to your <b>Team Code -> src -> main -> java -> org -> firstinspires -> ftc -> teamcode package</b>

## Instructions:

Sample Pipeline:
```Java
 public boolean sampleLeftPipeline() {
   //Margin of Error Array:
   int lightingArray[] = { 25, 25, 25 };

   //Gets the Bitmap and RGB Values:
   Bitmap objectImage = VuforiaImageInit.getImage(0.1);
   int leftRGBArray[][] = VuforiaImageInit.getRGBArray(objectImage, 12, 20, 20, 16);

   //Detects an Object and Returns:
   boolean foundLeft = imageInit.detect(leftRGBArray, lightingArray, 20);
   return foundLeft;
 }
```
