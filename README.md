# VintedHomework

This is a Java program created as a homework assignment to apply for the Vinted Academy position. The program processes an input file containing transaction operations, calculates discounts according to the given rules, and outputs the final results as a list of transactions with applied discounts.

## Compile and Run

To compile and run this program, Java 21 or later must be installed on your operating system.

1. Navigate to the `src` directory of the program.
2. Use the command line to compile the program with the following command:

   ```bash
   javac task/*.java
   ```
3. From the same directory, run the program using command:
  ```bash
    java task.Main
   ```
When the program runs, it will prompt you to provide an input file.

## Input
You must provide an input text file that contains a list of transactions. Each transaction should be on a separate line, with its parameters separated by whitespace. The required parameters for each transaction are:

- **Date** (without hours) in ISO format chronologically ordered from earliest to latest.
- **Package size code** (`S`, `M`, or `L`).
- **Carrier code** (`LP` or `MR`).

### Example of an Input Line

```bash
  2015-02-01 S MR
   ```
## Output
The program calculates discounts according to three main rules:

- All `S` shipments should always match the lowest `S` package price among the providers.
- The third `L` shipment via `LP` should be free, but only once per calendar month.
- Accumulated discounts cannot exceed 10 € in a calendar month. If there are not enough funds to fully cover a discount in the current calendar month, it will be partially covered.

If all parameters in a line of the input file are correct, the output will contain:

- **Date** (without hours) in ISO format.
- **Package size code** (`S`, `M`, or `L`).
- **Carrier code** (`LP` or `MR`).
- If a discount can be applied: the reduced shipment price; if not: the original price.
- If a discount was applied: the amount of the discount; if not, the program will print out `-`.

If the parameters are not correct, the program will output the original text from the input line followed by `ignored`.

### Examples of output:
```bash
  2015-02-01 S MR 1.50 0.50
  2015-02-29 CUSPS ignored
   ```
## Testing
Test were written for this program using JUnit.
