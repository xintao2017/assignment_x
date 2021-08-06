package common;

/**
 * Define InvalidInputException for invalid input
 *
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-07-28
 */

import java.lang.Exception;


public class InvalidInputException extends Exception {
    public InvalidInputException(String errMessage) {
        super(errMessage);
    }
}