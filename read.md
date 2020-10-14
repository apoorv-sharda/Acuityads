The framework is a Page Object Model framework which uses Page Factory.

1. There is a Properties file config.properties that stores information that remains static throughout the framework, such as Browser specific information, url etc.
2. There is a Base class (TestSetup.java) that deals with all the common functions used by all the pages. In "TechnicalComponents" Class I have extended Base class. This class is responsible for loading the configurations from properties files, Initializing the Web Driver, Implicit Waits, explicit waits etc.
3. Technical Components class: This stores and handles the functions or code which are repititive in nature such as waits, click, capturing Screenshots and many more which can be commonly used across the entire framework.The reason behind creating utility class is reusability. I have created all the methods in static so that we can directly access using class names instead of creating object.
