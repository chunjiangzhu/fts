diary lapsmolog.txt

% import y and signal corresponding to sd=0.1=10^{-1}
% -1 is default and is ignored in the names
load y.txt
load signal.txt

% import y and signal corresponding to sd=0.01=10^{-2}
% load y-2.txt
% load signal-2.txt
% y=y_2;
% signal=signal_2;

lambda = 0.01;
len = length(y);

%t=1
load L_G_SPA_t1.txt
L_G = spconvert(L_G_SPA_t1);

disp 'L_G_SPA_t1'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t1.txt
L_G = spconvert(L_SPA_t1);

disp 'L_SPA_t1'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t1.txt
L_G = spconvert(L_FTSPA_t1);

disp 'L_FTSPA_t1'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=2
load L_G_SPA_t2.txt
L_G = spconvert(L_G_SPA_t2);

disp 'L_G_SPA_t2'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t2.txt
L_G = spconvert(L_SPA_t2);

disp 'L_SPA_t2'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t2.txt
L_G = spconvert(L_FTSPA_t2);

disp 'L_FTSPA_t2'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=3
load L_G_SPA_t3.txt
L_G = spconvert(L_G_SPA_t3);

disp 'L_G_SPA_t3'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t3.txt
L_G = spconvert(L_SPA_t3);

disp 'L_SPA_t3'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t3.txt
L_G = spconvert(L_FTSPA_t3);

disp 'L_FTSPA_t3'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=4
load L_G_SPA_t4.txt
L_G = spconvert(L_G_SPA_t4);

disp 'L_G_SPA_t4'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t4.txt
L_G = spconvert(L_SPA_t4);

disp 'L_SPA_t4'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t4.txt
L_G = spconvert(L_FTSPA_t4);

disp 'L_FTSPA_t4'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=5
load L_G_SPA_t5.txt
L_G = spconvert(L_G_SPA_t5);

disp 'L_G_SPA_t5'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t5.txt
L_G = spconvert(L_SPA_t5);

disp 'L_SPA_t5'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t5.txt
L_G = spconvert(L_FTSPA_t5);

disp 'L_FTSPA_t5'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=6
load L_G_SPA_t6.txt
L_G = spconvert(L_G_SPA_t6);

disp 'L_G_SPA_t6'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t6.txt
L_G = spconvert(L_SPA_t6);

disp 'L_SPA_t6'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t6.txt
L_G = spconvert(L_FTSPA_t6);

disp 'L_FTSPA_t6'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=7
load L_G_SPA_t7.txt
L_G = spconvert(L_G_SPA_t7);

disp 'L_G_SPA_t7'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t7.txt
L_G = spconvert(L_SPA_t7);

disp 'L_SPA_t7'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t7.txt
L_G = spconvert(L_FTSPA_t7);

disp 'L_FTSPA_t7'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=8
load L_G_SPA_t8.txt
L_G = spconvert(L_G_SPA_t8);

disp 'L_G_SPA_t8'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t8.txt
L_G = spconvert(L_SPA_t8);

disp 'L_SPA_t8'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t8.txt
L_G = spconvert(L_FTSPA_t8);

disp 'L_FTSPA_t8'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=9
load L_G_SPA_t9.txt
L_G = spconvert(L_G_SPA_t9);

disp 'L_G_SPA_t9'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t9.txt
L_G = spconvert(L_SPA_t9);

disp 'L_SPA_t9'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t9.txt
L_G = spconvert(L_FTSPA_t9);

disp 'L_FTSPA_t9'
lapsmo_mse(lambda,L_G,len,y,signal);

%t=10
load L_G_SPA_t10.txt
L_G = spconvert(L_G_SPA_t10);

disp 'L_G_SPA_t10'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_SPA_t10.txt
L_G = spconvert(L_SPA_t10);

disp 'L_SPA_t10'
lapsmo_mse(lambda,L_G,len,y,signal);

load L_FTSPA_t10.txt
L_G = spconvert(L_FTSPA_t10);

disp 'L_FTSPA_t10'
lapsmo_mse(lambda,L_G,len,y,signal);

diary off