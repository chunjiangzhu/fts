# Improved Dynamic Graph Learning through Fault-Tolerant Sparsification


This is the source code for the experiments in the paper 'Improved dynamic graph learning through fault-tolerant sparsification', published in ICML 2019. There are three folders, data, java and matlab, which contain data, java code and matlab code respectively.

# Usages

  - Please put the files under data folder in the Maltalb working directory. Then one can run lapsmo.m to get accurary results for Laplacian-regularized estimation, or run ssl.m to get accurary results for graph SSL.
  - As above, one can use the files under data folder directly. Or one can generate these files by running the Java code and at the same time get update time results. Specifically, under the project in /java/ftss folder, SignalGeneration.java can be used to generate signals and obvervations y from an input graph, while SparsifierGeneration.java can be used to construct, for every time point, a sparsifier maintained from a fault-tolerant sparsifier and a sparisifier from scratch .

# Further Instructions

  - For Laplacian-regularized estimation in lapsmo.m, one may intend to load signals with different noises sigma \in \{10^{-3},10^{-2},10^{-1},10^{0}\}. To load the signal with the default sigma=10^{-1}, one can use "load y.txt" and "load signal.txt". To load the others, e.g., sigma=10^{-2}, one can use "load y-2.txt" and "load signal-2.txt". Please refer to README under data folder for the naming convention. Lambda can be set in Line 14.
  - For graph SSL in ssl.m, data structures corresponding to different sigma, e.g., "y_ssl" for sigma=10^{-1} and "y_ssl-2" for sigma=10^{-2}, can be constructed by y-ssl.m. Lambda can be set in Line 10.
  - For Java code SignalGeneration.java, the input graph and noise standard deviation sigma can be set in Lines 110 and 112, respectively.
  - For Java code SparsifierGeneration.java, the parameters can be tuned in Lines 111-119.

