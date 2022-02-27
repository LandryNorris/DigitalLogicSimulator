Digital Logic Simulator
=======================

This project provides a simulator for digital circuits. It currently provides a basic engine that assumes digital 
circuits are purely binary 

Ways to improve the model
-------------------------

1. We could create a shift buffer for the chips, which allows us to easily account for propagation delay of chips
2. We could store the value as a number, with a cutoff defining high and low. This accounts for transients
3. We could store the value as a number, changing exponentially according to the response of an RC Circuit
4. We could use the exponential model from 3 and define the middle range where the output is unchanged

Idea 4 would provide the most realism, but would add overhead to the calculation of state.