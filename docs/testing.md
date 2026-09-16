# Testing NutriSoy / Soya

## Automated checks

Use Java 25. On the project macOS setup:

```bash
sdk use java 25.0.3.fx-zulu
./gradlew clean build
```

The build runs JUnit and Checkstyle. The test report is at
`build/reports/tests/test/index.html`.

Tests cover command dispatch and execution, input and index boundaries,
duplicate detection, task identity and formatting, tag operations, storage
compatibility and malformed records, failed saves, application restarts,
console interaction, output capture, and GUI response error metadata.

Storage tests use JUnit temporary directories. Tests that change console
streams or the default locale restore them in `finally` blocks. Keep test
execution sequential because these resources are shared by the JVM.

No numerical coverage percentage is claimed. JavaFX layout, launchers,
OS-specific permissions, disk-full conditions, and actual display behavior
are outside the automated suite.

## Manual checklist

These checks are instructions, not a record of tests performed. Use a separate
working directory or a backup of your data file for manual testing.

| Area | Action | Expected result |
| --- | --- | --- |
| Startup | Launch with Java 25 using `./gradlew run` | Soya greeting, input and Do it button appear |
| Resize | Resize from the minimum window size to a large window | Input remains usable and long replies wrap |
| Scroll | Add enough tasks and messages to fill the window | Latest reply is visible and older replies can be read |
| Error display | Enter `deadline report /by 2026-02-30` | Helpful error appears with error styling |
| Recovery | Follow that error with `todo read book` | Success response appears and the input remains usable |
| Persistence | Add all task types, tag and mark them, exit, reopen, then `list` | Task details and state survive restart |
| Platforms | Run on each available supported OS with Java 25 and JavaFX | App launches and text remains readable |
| Language | Run under English and Chinese OS language settings | Commands, dates and saved Unicode descriptions remain usable |
| Permissions | Make a test data directory unwritable and attempt a save | Save failure is reported; record whether recovery works after access is restored |

Record OS, Java version, screen size, language setting, result, and any issue
when performing manual checks. Do not count an unperformed check as passed.
