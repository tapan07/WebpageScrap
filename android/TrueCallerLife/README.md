# Android WebPage Scraping  
  
Android Web Page Scraping is a project to scrap the content of any web pages.  We have scrap the content of the following url:

* TrueCaller **[# LIFE AS AN ANDROID ENGINEER]([https://truecaller.blog/2018/01/22/life-as-an-android-engineer/](https://truecaller.blog/2018/01/22/life-as-an-android-engineer/))** 

  
### Project Specifications
  
It reads the content of webpage & display the following outputs:
* Find the 10th character of the content.
* Find every 10th character i.e. 10th, 20th, 30th, etc.
* Count the occurrence of every unique word.
  
## Design & Library used

  
The app followed the MVP design pattern to implement the features. It used retrofit & rxJava2 for API calls. Maintaining proper coding separation to implement feature unit test. It uses Jsoup library for parsing the html content into normal text.

## Unit Test
  
The app uses Mockito, Powermockito & jUnit to test the functionality.
Below the reference of code coverage:
<p align="center">
<img src="[https://photos.app.goo.gl/MWNDbm1621nsUEuY6]"/>
</p>

```