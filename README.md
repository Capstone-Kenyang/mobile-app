# mobile-app
This is Kenyang mobile-app repository. Scroll to bottom of this markdown to see how to run this project.

# Kenyang

Kenyang is a mobile app that aims to reduce food waste and support communities in need by utilizing surplus food from restaurants and bakeries. With Kenyang, users can buy excess food at a discounted price or donate it to those in need, thus helping to create a more sustainable and inclusive food system.<br><br>

<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/2a77a420-21ad-4eb5-ae0e-6aee3760b6a4" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/d838c809-10f0-430d-a270-5247908c9946" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/5cd63265-0f72-450a-b9ba-a7ebdbda362f" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/d58e48bf-447b-4fca-ba14-d111996b60c0" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/8d7ae769-2188-4627-b7f6-e24215770f71" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/05ae8a35-d2cc-4d02-a69f-90f7a1208bab" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/17ff559d-56fb-4a7c-8876-dbb48bb0b8e9" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/4091f17d-dfd6-45e5-9b3b-9195b69f8fa8" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/fc0463dc-cbea-4e59-a461-592a0ce2e1f7" width="25%">
<img src="https://github.com/Capstone-Kenyang/.github/assets/117462539/35ea0665-3e07-4ef5-aac3-e2669ae5e50a" width="25%">


## What is Kenyang?
Every year, millions of tons of food are wasted in restaurants and bakeries due to overproduction or approaching expiration dates. This is a huge problem that goes against efforts to tackle hunger and reduce food waste. Kenyang is here to tackle this problem in a way:

- **Surplus Food Purchase:** Kenyang provides a platform users can buy discounted price surplus food from food vendors. This not only helps reduce food waste, but also allows consumers to enjoy food at a more affordable price.
  
- **Food Donation:** Users can purchase surplus food through Kenyang and donate it to the needy. These donations are managed by restaurants or bakeries, ensuring the food reaches those who need it most.

- **Food Safety Check:** Kenyang is equipped with a Machine Learning-based food safety scanning feature that allows users to verify if food is safe for consumption. By simply taking a photo of the food, the app will analyze and provide information regarding the condition of the food.

- **Food Category:** This app covers a wide range of food categories to meet diverse needs, including:
  - **Rice Dish:** Rice and various rice-based dishes.
  - **Bread and Pastry:** Various types of bread and pastries.
  - **Fruits:** Various types of fruits.
  - **Vegies:** Various types of vegetables.

With Kenyang, we aim to reduce food waste, alleviate hunger, and create a more sustainable and inclusive food system.<br><br>


## Features

### Food Safety Scanning

- Kenyang app is equipped with a food safety scanning feature that uses Machine Learning technology to verify food safety before consumption.
  
- **How to use the feature:**
  1. Click the camera icon on the bottom navigation bar of Kenyang app.
  2. Take a photo of the food you want to check.
  3. The app automatically analyzes the photo to determine the safety of the food.
  4. The analysis results are displayed within seconds, providing information on whether the food is safe for consumption or not.

### Food Sales

- Kenyang provides a platform to buy surplus food at discounted prices from various sellers, including restaurants, bakeries, fruit sellers, and vegetable sellers.
- Extensive food categories include:
  - **Rice Dishes:** Various types of delicious rice dishes.
  - **Bread and Cakes:** Fresh bread and a variety of appetizing cakes.
  - **Fruits:** Fresh fruits ready for consumption.
  - **Vegetables:** Fresh vegetables that are rich in nutrients.
    
### Donation Options

- Kenyang allows users to not only purchase surplus food at a discounted price, but also donate it to those in need.
- The donation process is managed by the food seller.

### Show Distance Based on Location

- Kenyang app allows users to view the distance between their location and the listed restaurants or bakeries after tehy turn on the location permission.
- This helps users in choosing the place closest to them, minimizing travel time.

### Check Restaurant Location on Google Maps

- Users can access the location information of the listed restaurants or bakeries directly through the Kenyang app.
- This allows users to review the map and determine the best route to reach the venue.

### Sort Menu in Categories Based on Highest Rating or Lowest Distance

- This feature allows users to sort food menus in specific categories based on the highest rating or lowest distance from the user's location.
- Users can easily find and order the most recommended food or the closest to them.

### Order List

- Kenyang app provides an order list feature that allows users to view their order history.
- Users can access details of previous orders, including food purchased, amount paid or donation status.<br><br>

## Technologies Used:
- ML: TensorFlow Google Colab
- CC: Google Cloud, Google Storage, Cloud Run
- MD: Retrofit2, Material 3, Google Play Service Location API, Firebase

## Additional Info
- Min SDK: API 24
- Target SDK: API 34
- Tested with AVD Pixel 7 API 35
- Tested with real device Realme 5i API 26<br><br>


## How to Run The Project
- Open Android Studio.
- Go to the Kenyang's MD Repository:
    - Open [github.com/Capstone-Kenyang/mobile-app](https://github.com/Capstone-Kenyang/mobile-app)
    - Click the green "Code" button and choose the "HTTPS" clone URL option. This URL will be used in the next step.
- Import the Project into Android Studio:

  - Using Git (Recommended):
      - In Android Studio, navigate to File > New > Project from Version Control > Git.
      - Paste the copied HTTPS clone URL into the "URL" field.
      - If prompted, provide your GitHub username and password (optional if using public repositories).
      - Click "Clone." Android Studio will download the project files.
      - Once complete, choose to either open the project in a new window or the current window.
        
  - Without Git:
      - In Android Studio, navigate to File > New > Import Project.
      - Browse to the downloaded project folder (typically a ZIP archive extracted from GitHub). Select the project's root directory (usually containing a file named "build.gradle").
      - Click "Next" and follow any on-screen prompts.
      - Sync Gradle (Optional):


- Run the Project:
    - Connect an Android Device or Emulator:
        - Make sure you have a physical Android device connected or an emulator running also make sure to use latest API and Google Play supported Emulator. You can manage these settings in Tools > Device Manager.
      
          > **Note**  Make sure your emulator device is logged in with a Google account (check the email in the emulator device to make sure) before running your Android app using Firebase Authentication!
        - Run the App: Click the "Run" button (usually a green play icon) in the toolbar. Select the appropriate device or emulator from the dropdown menu.
Android Studio will build and deploy the app to your chosen target.
