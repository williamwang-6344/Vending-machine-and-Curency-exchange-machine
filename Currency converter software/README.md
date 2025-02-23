# Currency Converter

This currency application provides a simple console based solution to converting between various currencies. It can display a large table of all currencies, or show a small selection of favourites. 

The application has two modes of use, User, and Admin. A user is able to see all the currencies and convert between them, while the admin user is able to make changes to the currency exchange rates.

Data is persisted through an external CSV file stored with the application.


## Build process

As this is a gradle based application, to build or edit, you need to first clone this repository to you computer using:
`git clone https://github.sydney.edu.au/SOT2412-COMP9412-2022S2/CC14_Group_02_Assignment_1.git`

The application can then be built from the base using `gradle clean build` and further run with `gradle run`.


## Testing

The application can be tested manually using the `gradle test` command. This will run the tests that are set up in the src/tests folder, and reports can be viewed on your local machine at:
`app/build/reports/tests/test/index.html`

And a coverage report can be found at: 
`app\build\reports\jacoco\test\html\index.html`

Automated testing takes place on a Jenkins instance located here: http://170.187.240.169:8080
Each push to the repository of any branch will trigger a build, which can be used to verify the new branch will build correctly and tests complete without issue. These builds should be checked before PRs are approved and merged.
