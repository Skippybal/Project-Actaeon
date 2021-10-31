# Project-Actaeon #
Project Actaeon is a java wrapper for three weka models. 
The program can take membrane characteristics like the area per lipid 
and then predicts certain membrane components using machine learning algorithms.

## Table of content

- [Installation](#installation)
    * [Prerequisites](#prerequisites)
    * [Dependencies](#dependencies)
- [Input file specifications](#Input-file-specifications)
- [Arguments](#arguments)
- [Contact](#contact)

## Installation
To install the program, simply pull this repo and use the following 
command to run the program. Of course, you will need to add your own arguments.

``` 
git pull https://github.com/Skippybal/Project-Actaeon.git
```

```
java -jar ActaeonLauncher [options]
```

### Prerequisites
* Java version 16 SDK
* gradle

### Dependencies
|Group ID                               |Artifact ID                            |Version            |   
|---                                    |---                                    |---                |
|commons-cli                            |commons-cli                            |1.4                |
|nz.ac.waikato.cms.weka                 |weka-stable                            |3.8.0              |
|commons-io                             |commons-io                             |2.6                |

## Arguments
| Parameter         | Description                                     | Type    | Default             |
| ---               | ---                                             | ---     | ---                 |
| -f --infile       | Input file, ``.arff`` or ``.csv``               | String  |                     |
| -o --outfile      | Output file, ``.arff`` or ``.csv``              | String  | ``data/output.csv`` |
| -p --print        | Print the results to the command line            |         |``false``           |
| -a --apl          | The apl of the membrane in nm^2                 | double  |                     |
| -t --thickness    | The thickness of the membrane in nm             | double  |                     |
| -b --bending      | The apl of the membrane in kB T                 | double  |                     |
| -i --tilt         | The tilt angle in degrees (&deg;)               | double  |                     |
| -z --zorder       | The z-order of the membrane                     | double  |                     |
| -c --compress     | The compressibility of the membrane in cN / m   | double  |                     |
| -h --help         | Print the help                                  |         | ``false``           |

**IMPORTANT**

* When an input file is specified, command line arguments like the apl and bending rigidity are ignored.
This is to prevent overwriting issues in the output
* When no output file is specified, the output is written to data/output.csv
* When option -p is given, the results will still be written to the outputfile, 
but now also to the command line
* Make sure to use a comma as a separator in doubles on the command line
* Options p and h both dont need a value, they are just given like -p and -h without a value


## Input file specifications
The input file needs to be either ``.csv`` or ``.arff``. **The order of the columns is important.**

In the case of ``.csv`` the file must contain the following columns, in this specific order:
```
* APL
* thickness
* bending
* tilt
* zorder
* compress
```

In the case of ``.arff`` the file must contain the following attributes, in this specific order:
```
* @attribute APL numeric
* @attribute thickness numeric
* @attribute bending numeric 
* @attribute tilt numeric 
* @attribute zorder numeric 
* @attribute compress numeric
```

## Contact

* K. Notebomer
* K.a.notebomer@st.hanze.nl