/*
 * This Spock specification was auto generated by running the Gradle 'init' task
 * by 'masa' at '16/11/21 7:56' with Gradle 3.2
 *
 * @author masa, @date 16/11/21 7:56
 */

import spock.lang.Specification

class LibraryTest extends Specification{
    def "someLibraryMethod returns true"() {
        setup:
        Library lib = new Library()
        when:
        def result = lib.someLibraryMethod()
        then:
        result == true
    }
}
