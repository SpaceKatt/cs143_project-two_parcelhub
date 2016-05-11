# Parcel Hub Project

A simple application emulating the receiving of Parcels and storing
their information, for my Java class (CS143/Spr16).

#### Project Objective:

To create a GUI that allows a user to scan, edit, and store Parcels in
multiple databases. This application must also provide basic functionalities
that are requisite for a Shipping Hub.

#### Features:

  - Users may scan, edit, remove, search for, and view the information of
    Parcels which make their way into a database.
  - XML is used for databases.
  - Multiple databases may be used to give users the chance to view Parcel
    listings from different days.
  - Databases may be renamed.
  - Users may search for Parcels by ID, Customer Name, and ZipCode.
  - Search by name and ZipCode spawns a new window which lists all results
    found. Upon closing this window, the selected Parcel will be found and
    highlighted in the main GUI Window.
  - Individual Parcel information, or the entire GUI, may be printed from the
    File menu.
  - Parcels are listed by state, and only states which contain Parcels are
    listed.
  - All Parcels may be viewed at once from the Action menu.
  - When viewing all Parcels, or search by name/zip results, Parcels may be
    sorted by ID, Customer Name, or ZipCode.
  - ToolTip Text is given for all buttons and all menus/menuItems.
  - Mnemonics are given for all buttons and all menus/menuItems.
  - Buttons are enabled/disabled based on whether their actions are valid
    within the current context of the application.
  - Splash screen is displayed on startup.
  - Parcels are sorted by ID in the State listings.
  - Changes to the database are dynamic in nature, e.g. Parcels that are
    deleted do not reappear when the application is restarted.
  - Fields from editing and scanning Parcels are validated with regular
    expressions.
  - Dancer deletion is confirmed before performed.
  - Parcel ID is generated using an MD5 digest, hence all IDs are unique
    between all databases. Even Parcels with the exact same information will
    produce unique IDs since the time of the Parcel being scanned is being fed
    into the cryptographic hash.

##### Contact:

    Thomas Kercheval -> spacekattpoispin@gmail.com

##### Notes:

  - This project is backed up on GitHub.
  - This project is licensed under GPL v3.0
  - This project was developed on Windows 10.
