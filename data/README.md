# Files

  - facebook_combined.txt and facebook_combined_weights.txt: an input graph downloaded from standard SNAP website and its weighted version, resp.
  - faults: simulated faults. Detailed format can be seen in SparsifierGeneration.java.
  - signal-0, signal, signal-2, signal-3 (y-0, y, y-2, y-3): signals (y) for sigma=10^0, 10^{-1}, 10^{-2}, 10^{-3}, resp. In the default sigma=10^{-1}, the suffix "-1" is omitted.
  - L_G_SPA and L_FTSPA_SPA: the Laplacians of the input graph G and its FTSPAsifier before updates, resp.
  - L_G_SPA_ti, L_SPA_ti and L_FTSPA_ti: the Laplacians of the graph, its sparsifier constructed from scratch and its sparsifier maintained by FT sparsifier resp., for the time point i
  - y_ssl and y_ssl-2: data structures used by ssl.m for sigma=10^{-1} and 10^{-2}, resp.
  - ftsslog and sslog: log files for lapsmo and ssl, resp.
