package com.mapbox.mapboxsdk.style.expressions;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;

import java.util.ArrayList;
import java.util.List;

public class Expression {

  protected final String operator;
  private final Object[] values;

  public Expression(@NonNull String operator, @Nullable Object... values) {
    this.operator = operator;
    this.values = values;
  }

  @NonNull
  public Object[] toArray() {
    List<Object> array = new ArrayList<>();
    array.add(operator);
    if (values != null) {
      for (Object o : values) {
        if (o instanceof Expression) {
          array.add(((Expression) o).toArray());
        } else {
          array.add(o);
        }
      }
    }
    return array.toArray();
  }

  //
  // Color
  //

  /**
   * Creates a color value from red, green, and blue components, which must range between 0 and 255, and an alpha component of 1. If any component is out of range, the expression is an error.
   *
   * @param expressions
   * @return
   */
  public static Expression rgb(Object... expressions) {
    return new Expression("rgb", expressions);
  }

  /**
   * Creates a color value from red, green, and blue components, which must range between 0 and 255, and an alpha component of 1. If any component is out of range, the expression is an error.
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  public static Expression rgb(Number red, Number green, Number blue) {
    return new Expression("rgb", red, green, blue);
  }

  /**
   * Creates a color value from red, green, blue components, which must range between 0 and 255, and an alpha component which must range between 0 and 1. If any component is out of range, the expression is an error.
   *
   * @param expressions
   * @return
   */
  public static Expression rgba(Object... expressions) {
    return new Expression("rgba", expressions);
  }

  /**
   * Creates a color value from red, green, blue components, which must range between 0 and 255, and an alpha component which must range between 0 and 1. If any component is out of range, the expression is an error.
   *
   * @param red
   * @param green
   * @param blue
   * @param alpha
   * @return
   */
  public static Expression rgba(Number red, Number green, Number blue, Number alpha) {
    return new Expression("rgba", red, green, blue, alpha);
  }

  /**
   * Returns a four-element array containing the input color's red, green, blue, and alpha components, in that order.
   *
   * @param expressions
   * @return
   */
  public static Expression toRgba(Object... expressions) {
    return new Expression("to-rgba", expressions);
  }

  /**
   * Returns a four-element array containing the input color's red, green, blue, and alpha components, in that order.
   *
   * @param color
   * @return
   */
  public static Expression toRgba(String color) {
    return new Expression("to-rgba", color);
  }

  /**
   * Returns a four-element array containing the input color's red, green, blue, and alpha components, in that order.
   *
   * @param color
   * @return
   */
  public static Expression toRgba(@ColorInt int color) {
    return new Expression("to-rgba", PropertyFactory.colorToRgbaString(color));
  }

  //
  // Decision
  //

  /**
   * Returns `true` if the input values are equal, `false` otherwise. The inputs must be numbers, strings, or booleans, and both of the same type.",
   *
   * @param expressions
   * @return
   */
  public static Expression eq(Object... expressions) {
    return new Expression("==", expressions);
  }

  /**
   * Returns `true` if the input values are not equal, `false` otherwise. The inputs must be numbers, strings, or booleans, and both of the same type.",
   *
   * @param expressions
   * @return
   */
  public static Expression neq(Object... expressions) {
    return new Expression("!=", expressions);
  }

  /**
   * Returns `true` if the first input is strictly greater than the second, `false` otherwise. The inputs must be numbers or strings, and both of the same type.
   *
   * @param expressions
   * @return
   */
  public static Expression gt(Object... expressions) {
    return new Expression(">", expressions);
  }

  /**
   * Returns `true` if the first input is strictly less than the second, `false` otherwise. The inputs must be numbers or strings, and both of the same type.
   *
   * @param expressions
   * @return
   */
  public static Expression lt(Object... expressions) {
    return new Expression("<", expressions);
  }

  /**
   * Returns `true` if the first input is greater than or equal to the second, `false` otherwise. The inputs must be numbers or strings, and both of the same type.
   *
   * @param expressions
   * @return
   */
  public static Expression gte(Object... expressions) {
    return new Expression(">=", expressions);
  }

  /**
   * Returns `true` if the first input is less than or equal to the second, `false` otherwise. The inputs must be numbers or strings, and both of the same type.
   *
   * @param expressions
   * @return
   */
  public static Expression lte(Object... expressions) {
    return new Expression("<=", expressions);
  }

  /**
   * Returns `true` if all the inputs are `true`, `false` otherwise. The inputs are evaluated in order, and evaluation is short-circuiting: once an input expression evaluates to `false`, the result is `false` and no further input expressions are evaluated.
   *
   * @param expressions
   * @return
   */
  public static Expression all(Object... expressions) {
    return new Expression("all", expressions);
  }

  /**
   * Returns `true` if any of the inputs are `true`, `false` otherwise. The inputs are evaluated in order, and evaluation is short-circuiting: once an input expression evaluates to `true`, the result is `true` and no further input expressions are evaluated.
   *
   * @param expressions
   * @return
   */
  public static Expression any(Object... expressions) {
    return new Expression("any", expressions);
  }

  /**
   * Logical negation. Returns `true` if the input is `false`, and `false` if the input is `true`.
   *
   * @param expressions
   * @return
   */
  public static Expression not(Object... expressions) {
    return new Expression("!", expressions);
  }

  /**
   * Selects the first output whose corresponding test condition evaluates to true.
   *
   * @param expressions
   * @return
   */
  public static Expression switchCase(Object... expressions) {
    return new Expression("case", expressions);
  }

  /**
   * Selects the output whose label value matches the input value, or the fallback value if no match is found. The `input` can be any string or number expression (e.g. `[\"get\", \"building_type\"]`). Each label can either be a single literal value or an array of values.
   *
   * @param expressions
   * @return
   */
  public static Expression match(Object... expressions) {
    return new Expression("match", expressions);
  }

  /**
   * Evaluates each expression in turn until the first non-null value is obtained, and returns that value.
   *
   * @param expressions
   * @return
   */
  public static Expression coalesce(Object... expressions) {
    return new Expression("coalesce", expressions);
  }

  //
  // FeatureData
  //

  /**
   * Gets the feature properties object.  Note that in some cases, it may be more efficient to use [\"get\", \"property_name\"] directly.
   *
   * @param expressions
   * @return
   */
  public static Expression properties(Object... expressions) {
    return new Expression("properties", expressions);
  }

  /**
   * Gets the feature's geometry type: Point, MultiPoint, LineString, MultiLineString, Polygon, MultiPolygon.",
   *
   * @param expressions
   * @return
   */
  public static Expression geometryType(Object... expressions) {
    return new Expression("geometry-type", expressions);
  }

  /**
   * Gets the feature's id, if it has one.
   *
   * @param expressions
   * @return
   */
  public static Expression id(Object... expressions) {
    return new Expression("id", expressions);
  }

  //
  // Heatmap
  //

  /**
   * Gets the kernel density estimation of a pixel in a heatmap layer, which is a relative measure of how many data points are crowded around a particular pixel. Can only be used in the `heatmap-color` property.
   *
   * @param expressions
   * @return
   */
  public static Expression heatmapDensity(Object... expressions) {
    return new Expression("heatmap-density", expressions);
  }

  //
  // Lookup
  //


  /**
   * Retrieves an item from an array.
   *
   * @param expressions
   * @return
   */
  public static Expression at(Object... expressions) {
    return new Expression("at", expressions);
  }

  /**
   * Retrieves a property value from the current feature's properties, or from another object if a second argument is provided. Returns null if the requested property is missing.
   *
   * @param expressions
   * @return
   */
  public static Expression get(Object... expressions) {
    return new Expression("get", expressions);
  }

  /**
   * Tests for the presence of an property value in the current feature's properties, or from another object if a second argument is provided.
   *
   * @param expressions
   * @return
   */
  public static Expression has(Object... expressions) {
    return new Expression("has", expressions);
  }

  /**
   * Tests for the presence of an property value in the current feature's properties, or from another object if a second argument is provided.
   *
   * @param expressions
   * @return
   */
  public static Expression length(Object... expressions) {
    return new Expression("length", expressions);
  }

  //
  // Math
  //

  /**
   * Returns mathematical constant ln(2).
   *
   * @return ln(2)
   */
  public static Expression ln2() {
    return new Expression("ln2", null);
  }

  /**
   * Returns the mathematical constant pi.
   *
   * @return pi
   */
  public static Expression pi() {
    return new Expression("pi", null);
  }

  /**
   * Returns the mathematical constant e.
   *
   * @return e
   */
  public static Expression e() {
    return new Expression("e", null);
  }

  /**
   * Returns the sum of the inputs.
   *
   * @param expressions
   * @return
   */
  public static Expression sum(Object... expressions) {
    return new Expression("+", expressions);
  }

  /**
   * Returns the sum of the inputs.
   *
   * @param numbers
   * @return
   */
  public static Expression sum(Number... numbers) {
    return new Expression("+", numbers);
  }

  /**
   * Returns the product of the inputs.
   *
   * @param expressions
   * @return
   */
  public static Expression product(Object... expressions) {
    return new Expression("*", expressions);
  }

  /**
   * Returns the product of the inputs.
   *
   * @param numbers
   * @return
   */
  public static Expression product(Number... numbers) {
    return new Expression("*", numbers);
  }

  /**
   * Returns the result of subtracting a number from 0.
   *
   * @param number
   * @return
   */
  public static Expression subtract(Number number) {
    return new Expression("-", number);
  }

  /**
   * Returns the result of subtracting the second input from the first
   *
   * @param first
   * @param second
   * @return
   */
  public static Expression subtract(Number first, Number second) {
    return new Expression("-", first, second);
  }

  /**
   * For two inputs, returns the result of subtracting the second input from the first. For a single input, returns the result of subtracting it from 0.
   *
   * @param expressions
   * @return
   */
  public static Expression subtract(Object... expressions) {
    return new Expression("-", expressions);
  }

  /**
   * Returns the result of floating point division of the first input by the second.
   *
   * @param expressions
   * @return
   */
  public static Expression division(Object... expressions) {
    return new Expression("/", expressions);
  }

  /**
   * Returns the result of floating point division of the first input by the second.
   *
   * @param first
   * @param second
   * @return
   */
  public static Expression division(Number first, Number second) {
    return new Expression("/", first, second);
  }

  /**
   * Returns the remainder after integer division of the first input by the second.
   *
   * @param expressions
   * @return
   */
  public static Expression mod(Object... expressions) {
    return new Expression("%", expressions);
  }

  /**
   * Returns the remainder after integer division of the first input by the second.
   *
   * @param first
   * @param second
   * @return
   */
  public static Expression mod(Number first, Number second) {
    return new Expression("%", first, second);
  }

  /**
   * Returns the result of raising the first input to the power specified by the second.
   *
   * @param expressions
   * @return
   */
  public static Expression pow(Object... expressions) {
    return new Expression("^", expressions);
  }

  /**
   * Returns the result of raising the first input to the power specified by the second.
   *
   * @param first
   * @param second
   * @return
   */
  public static Expression pow(Number first, Number second) {
    return new Expression("^", first , second);
  }

  /**
   * Returns the square root of the input
   *
   * @param expressions
   * @return
   */
  public static Expression sqrt(Object... expressions) {
    return new Expression("sqrt", expressions);
  }

  /**
   * Returns the square root of the input
   *
   * @param number
   * @return
   */
  public static Expression sqrt(Number number) {
    return new Expression("sqrt", number);
  }

  /**
   * Returns the base-ten logarithm of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression log10(Object... expressions) {
    return new Expression("log10", expressions);
  }

  /**
   * Returns the base-ten logarithm of the input.
   *
   * @param number
   * @return
   */
  public static Expression log10(Number number) {
    return new Expression("log10", number);
  }

  /**
   * Returns the natural logarithm of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression ln(Object... expressions) {
    return new Expression("ln", expressions);
  }

  /**
   * Returns the natural logarithm of the input.
   *
   * @param number
   * @return
   */
  public static Expression ln(Number number) {
    return new Expression("ln", number);
  }

  /**
   * Returns the base-two logarithm of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression log2(Object... expressions) {
    return new Expression("log2", expressions);
  }

  /**
   * Returns the base-two logarithm of the input.
   *
   * @param number
   * @return
   */
  public static Expression log2(Number number) {
    return new Expression("log2", number);
  }

  /**
   * Returns the sine of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression sin(Object... expressions) {
    return new Expression("sin", expressions);
  }

  /**
   * Returns the sine of the input.
   *
   * @param number
   * @return
   */
  public static Expression sin(Number number) {
    return new Expression("sin", number);
  }

  /**
   * Returns the cosine of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression cos(Object... expressions) {
    return new Expression("cos", expressions);
  }

  /**
   * Returns the cosine of the input.
   *
   * @param number
   * @return
   */
  public static Expression cos(Number number) {
    return new Expression("cos", number);
  }

  /**
   * Returns the tangent of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression tan(Object... expressions) {
    return new Expression("tan", expressions);
  }

  /**
   * Returns the tangent of the input.
   *
   * @param number
   * @return
   */
  public static Expression tan(Number number) {
    return new Expression("tan", number);
  }

  /**
   * Returns the arcsine of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression asin(Object... expressions) {
    return new Expression("asin", expressions);
  }

  /**
   * Returns the arcsine of the input.
   *
   * @param number
   * @return
   */
  public static Expression asin(Number number) {
    return new Expression("asin", number);
  }

  /**
   * Returns the arccosine of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression acos(Object... expressions) {
    return new Expression("acos", expressions);
  }

  /**
   * Returns the arccosine of the input.
   *
   * @param number
   * @return
   */
  public static Expression acos(Number number) {
    return new Expression("acos", number);
  }

  /**
   * Returns the arctangent of the input.
   *
   * @param expressions
   * @return
   */
  public static Expression atan(Object... expressions) {
    return new Expression("atan", expressions);
  }

  /**
   * Returns the arctangent of the input.
   *
   * @param number
   * @return
   */
  public static Expression atan(Number number) {
    return new Expression("atan", number);
  }

  /**
   * Returns the minimum value of the inputs.
   *
   * @param expressions
   * @return
   */
  public static Expression min(Object... expressions) {
    return new Expression("min", expressions);
  }

  /**
   * Returns the minimum value of the inputs.
   *
   * @param numbers
   * @return
   */
  public static Expression min(Number... numbers) {
    return new Expression("min", numbers);
  }

  /**
   * Returns the maximum value of the inputs.
   *
   * @param expressions
   * @return
   */
  public static Expression max(Object... expressions) {
    return new Expression("max", expressions);
  }

  /**
   * Returns the maximum value of the inputs.
   *
   * @param numbers
   * @return
   */
  public static Expression max(Number... numbers) {
    return new Expression("max", numbers);
  }

  //
  // String
  //

  /**
   * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the locale-insensitive case mappings in the Unicode Character Database.
   *
   * @param expressions
   * @return
   */
  public static Expression upcase(Object... expressions) {
    return new Expression("upcase", expressions);
  }

  /**
   * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the locale-insensitive case mappings in the Unicode Character Database.
   *
   * @param string
   * @return
   */
  public static Expression upcase(String string) {
    return new Expression("upcase", string);
  }

  /**
   * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the locale-insensitive case mappings in the Unicode Character Database.
   *
   * @param expressions
   * @return
   */
  public static Expression downcase(Object... expressions) {
    return new Expression("downcase", expressions);
  }

  /**
   * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the locale-insensitive case mappings in the Unicode Character Database.
   *
   * @param string
   * @return
   */
  public static Expression downcase(String string) {
    return new Expression("downcase", string);
  }

  /**
   * Returns a string consisting of the concatenation of the inputs.
   *
   * @param expressions
   * @return
   */
  public static Expression concat(Object... expressions) {
    return new Expression("concat", expressions);
  }

  /**
   * Returns a string consisting of the concatenation of the inputs.
   *
   * @param strings
   * @return
   */
  public static Expression concat(String... strings) {
    return new Expression("concat", strings);
  }

  //
  // Types
  //

  /**
   * Provides a literal array or object value.
   *
   * @param expressions
   * @return
   */
  public static Expression literal(Object... expressions) {
    return new Expression("literal", expressions);
  }

  /**
   * Asserts that the input is an array (optionally with a specific item type and length).  If, when the input expression is evaluated, it is not of the asserted type, then this assertion will cause the whole expression to be aborted.
   *
   * @param expressions
   * @return
   */
  public static Expression array(Object... expressions) {
    return new Expression("array", expressions);
  }

  /**
   * Returns a string describing the type of the given value.
   *
   * @param expressions
   * @return
   */
  public static Expression typeOf(Object... expressions) {
    return new Expression("typeof", expressions);
  }

  /**
   * Asserts that the input value is a string. If multiple values are provided, each one is evaluated in order until a string value is obtained. If none of the inputs are strings, the expression is an error.
   *
   * @param expressions
   * @return
   */
  public static Expression string(Object... expressions) {
    return new Expression("string", expressions);
  }

  /**
   * Asserts that the input value is a number. If multiple values are provided, each one is evaluated in order until a number value is obtained. If none of the inputs are numbers, the expression is an error.
   *
   * @param expressions
   * @return
   */
  public static Expression number(Object... expressions) {
    return new Expression("number", expressions);
  }

  /**
   * Asserts that the input value is a boolean. If multiple values are provided, each one is evaluated in order until a boolean value is obtained. If none of the inputs are booleans, the expression is an error.
   *
   * @param expressions
   * @return
   */
  public static Expression bool(Object... expressions) {
    return new Expression("boolean", expressions);
  }

  /**
   * Asserts that the input value is an object. If it is not, the expression is an error
   *
   * @param expressions
   * @return
   */
  public static Expression object(Object... expressions) {
    return new Expression("object", expressions);
  }

  /**
   * Converts the input value to a string. If the input is `null`, the result is `\"null\"`. If the input is a boolean, the result is `\"true\"` or `\"false\"`. If the input is a number, it is converted to a string as specified by the [\"NumberToString\" algorithm](https://tc39.github.io/ecma262/#sec-tostring-applied-to-the-number-type) of the ECMAScript Language Specification. If the input is a color, it is converted to a string of the form `\"rgba(r,g,b,a)\"`, where `r`, `g`, and `b` are numerals ranging from 0 to 255, and `a` ranges from 0 to 1. Otherwise, the input is converted to a string in the format specified by the [`JSON.stringify`](https://tc39.github.io/ecma262/#sec-json.stringify) function of the ECMAScript Language Specification.
   *
   * @param expressions
   * @return
   */
  public static Expression toString(Object... expressions) {
    return new Expression("to-string", expressions);
  }

  /**
   * Converts the input value to a number, if possible. If the input is `null` or `false`, the result is 0. If the input is `true`, the result is 1. If the input is a string, it is converted to a number as specified by the [\"ToNumber Applied to the String Type\" algorithm](https://tc39.github.io/ecma262/#sec-tonumber-applied-to-the-string-type) of the ECMAScript Language Specification. If multiple values are provided, each one is evaluated in order until the first successful conversion is obtained. If none of the inputs can be converted, the expression is an error.
   *
   * @param expressions
   * @return
   */
  public static Expression toNumber(Object... expressions) {
    return new Expression("to-number", expressions);
  }

  /**
   * "Converts the input value to a boolean. The result is `false` when then input is an empty string, 0, `false`, `null`, or `NaN`; otherwise it is `true`.
   *
   * @param expressions
   * @return
   */
  public static Expression toBool(Object... expressions) {
    return new Expression("to-boolean", expressions);
  }

  /**
   * Converts the input value to a color. If multiple values are provided, each one is evaluated in order until the first successful conversion is obtained. If none of the inputs can be converted, the expression is an error.
   *
   * @param expressions
   * @return
   */
  public static Expression toColor(Object... expressions) {
    return new Expression("to-color", expressions);
  }

  //
  // Variable binding
  //

  /**
   * Binds expressions to named variables, which can then be referenced in the result expression using [\"var\", \"variable_name\"].
   *
   * @param expressions
   * @return
   */
  public static Expression let(Object... expressions) {
    return new Expression("let", expressions);
  }

  /**
   * References variable bound using \"let\"
   *
   * @param expressions
   * @return
   */
  public static Expression var(Object... expressions) {
    return new Expression("var", expressions);
  }

  //
  // Zoom
  //

  /**
   * Gets the current zoom level.  Note that in style layout and paint properties, [\"zoom\"] may only appear as the input to a top-level \"step\" or \"interpolate\" expression.
   *
   * @param expressions
   * @return
   */
  public static Expression zoom(Object... expressions) {
    return new Expression("zoom", expressions);
  }

  //
  // Ramps, scales, curves
  //

  /**
   * Produces discrete, stepped results by evaluating a piecewise-constant function defined by pairs of input and output values (\"stops\"). The `input` may be any numeric expression (e.g., `[\"get\", \"population\"]`). Stop inputs must be numeric literals in strictly ascending order. Returns the output value of the stop just less than the input, or the first input if the input is less than the first stop.
   *
   * @param expressions
   * @return
   */
  public static Expression step(Object... expressions) {
    return new Expression("step", expressions);
  }

  /**
   * Produces continuous, smooth results by interpolating between pairs of input and output values (\"stops\"). The `input` may be any numeric expression (e.g., `[\"get\", \"population\"]`). Stop inputs must be numeric literals in strictly ascending order. The output type must be `number`, `array<number>`, or `color`.\n\nInterpolation types:\n- `[\"linear\"]`: interpolates linearly between the pair of stops just less than and just greater than the input.\n- `[\"exponential\", base]`: interpolates exponentially between the stops just less than and just greater than the input. `base` controls the rate at which the output increases: higher values make the output increase more towards the high end of the range. With values close to 1 the output increases linearly.\n- `[\"cubic-bezier\", x1, y2, x2, y2]`: interpolates using the cubic bezier curve defined by the given control points.
   *
   * @param expresions
   * @return
   */
  public static Expression interpolate(Object... expresions) {
    return new Expression("interpolate", expresions);
  }

}
