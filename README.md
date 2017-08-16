<h1>Neovolve:</h1>

Neovolve is a Genetic Programming framework that uses unit tests as a fitness function to evolve programs. Using formal functional acceptance unit tests as fitness functions confronts programming from the purest set of criteria; that of specifying the 'proof' of the working program. As Alan Turing theorized, if you write the code that proves the program, you have after a fashion written the program itself. By specifying a complete enough set of unit tests and starting with the domain of possible operations, a genetic programming solution can be converged upon by statistical methods. As the complexity of our systems exceed the threshold of comprehensibility, such a framework would seem to be the future of programming. The name Extreme Genetic Programming borrows from the Extreme Programming paradigm by proposing a completely test-first design environment complete enough to allow the machine to devise the code.

Neovolve as an Extreme Genetic Programming Framework:

Neovolve is a modified version of the 'jgprog' Java Genetic Programming framework from SourceForge. Modifications include enrichment of the data types, function set, and addition of changes making it more amenable to developing solutions based on generic unit tests. The FIT testing framework was also integrated to vastly simplify unit test construction and provide a browser-based interface.

A major objective is for the unit tests to be transparent to the actual mechanics of the solution. We don't want the user to have to guess what might be necessary to solve the problem. In fact, we don't want the user to have to know anything about how to code the solution. We only want them to design unit tests and program as complete a test harness as possible. Neovolve will only ever know the values being tested and the type returned from the evolving code. As the code evolves, the results of running it with the values under test is checked against expected results. This process continues until all the tests are solved by the evolved code. Surprising , unexpected, emergent behaviour arises from the machine's attempt at optimal solving. Behaviour which often exceeds the parameters of the objective tests.

The Unit Test as a Fitness Function

Extreme Programming is a concept pioneered by Kent Beck and Ward Cunningham which has gained worldwide acceptance. It is an agile software methodology that espouses fundamental goals and how best to achieve them. The precepts of XP are as follows:

<p/>
Coding. 
At the end of the day, if the program doesn't run and make money for the client, you haven't done anything.
Testing. You have to know when you're done. The tests tell you this. If you're smart, you'll write them first so you'll know the instant you're done. Otherwise, you're stuck thinking you maybe might be done, but knowing you're probably not, but you're not sure how close you are.
Listening. You have to learn what the problem is in the first place, then you have to learn what numbers to put in the tests. You probably won't know this yourself, so you have to get good at listening to clients - users, managers, and business people.
Designing. You have to take what your program tells you about how it wants to be structured and feed it back into the program. Otherwise, you'll sink under the weight of your own guesses. Listening, Testing, Coding, Designing. That's all there is to software. Anyone who tells you different is selling something.
-- Kent Beck, author of "Extreme Programming Explained"
<p/>
Genetic programming is a model of programming which uses the ideas (and some of the terminology) of biological evolution to handle a complex problem. Of a number of possible programs (usually small program functions within a larger application), the most effective programs survive and compete or crossbreed with other programs to continually approach closer to the needed solution. Genetic programming can be viewed as an extension of the Genetic Algorithm, a model for testing and selecting the best choice among a set of results. Genetic programming goes a step farther and makes the program or "function" the unit that is tested. Two approaches are used to select the successful program - crossbreeding and the tournament or competition approach. A difficult part of using genetic programming is determining the fitness function, the degree to which a program is helping to arrive at the desired goal. A simple example of a genetic programming fitness function would be devising a program to fire a gun. The distance by which the bullet misses its target would determine the fitness function.

The commonality in these two definitions are 'unit test' and 'program' so it seems natural to consider the unit test as a fitness function and hence XP a natural methodology to advance GP.

So Genetic Programming is a Genetic Algorithm performed on a parse tree of executable program code. The fitness is determined by how well the code solves a problem. In XGP, the problem is expressed as a unit test. The unit test is the fitness function. The problems that can be solved are any problem that is solvable by the set of functions in the system, in this case anything solvable by most programming languages.


Essentially, GP is a form of optimization toward the goal of converging upon a 'solution', using optimization processes borrowed from an old model built by a power higher and smarter than we. They can, at times, be erratic yet predicable in their inevitable march toward a goal. Part of the optimization is using a 'fitness function' to select the best candidates from a 'population' of 'individuals'. In our case the 'fitness function' is built as a unit test for a specific piece of code functionality, and we converge on the solution to those tests. The Individuals have 'chromosomes' composed of functions and terminals in a parse tree that represent an executable piece of code in the framework.

During reproduction, branches of code from the chromosomes are crossed such that they do not violate programmatic structural integrity and new code is produced. Code that passes the units tests are given varying degrees of fitness by the test. In this way the 'optimum' solution is converged upon. In reality it tends to be highly specific to the unit test, thus enforcing the design methodology espoused in XP.

To initially start the operation we take what we know about the test, the return type and the argument types or variable types, and we use those to randomly populate the starting population with all possible functions for that type. We then inject the variables. Using that set we converge on the return type, and the variables, constants, and return types of functions guide through structural integrity. In the end the code is structurally complete. Execution and check of results in unit test fitness functions, along with exception fitness reduction function, allows us to produce a final raw fitness, which, using a standardization formula, is rolled into the final fitness for that individual. The proportionally fittest individuals are selected from the population to reproduce and the next generation begins.

Write a complete unit test and the code evolves to solve it. Since humans don't write code quickly or easily, it seems like a good job for a computer. All we organics have to do is figure out how to write unit tests correctly.

In contrast to something like bridge engineering, construction of code is but a fraction of the cost of a project. Regardless, it is the adherence to the philosophy of building complete units tests and allowing them to enforce design that is the truly powerful aspect of XP and XGP.

A discussion of Extreme Programming can be found at:

http://www.c2.com/cgi/wiki?ExtremeProgramming

A Genetic Programming FAQ can be found at: 

http://www.cs.ucl.ac.uk/research/genprog/gp2faq/gp2faq2.html#Q1