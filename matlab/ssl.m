diary sslog.txt

% import data structures corresponding to sd=0.01=10^{-1}
% -1 is default and is ignored in the name
load y_ssl

% import data structures corresponding to sd=0.01=10^{-2}
%load y_ssl-2

lambda = 0.000001;


%t=1
load L_G_SPA_t1.txt
L_G = spconvert(L_G_SPA_t1);

disp 'L_G_SPA_t1'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t1.txt
L_G = spconvert(L_SPA_t1);

disp 'L_SPA_t1'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t1.txt
L_G = spconvert(L_FTSPA_t1);

disp 'L_FTSPA_t1'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=2
load L_G_SPA_t2.txt
L_G = spconvert(L_G_SPA_t2);

disp 'L_G_SPA_t2'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t2.txt
L_G = spconvert(L_SPA_t2);

disp 'L_SPA_t2'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t2.txt
L_G = spconvert(L_FTSPA_t2);

disp 'L_FTSPA_t2'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=3
load L_G_SPA_t3.txt
L_G = spconvert(L_G_SPA_t3);

disp 'L_G_SPA_t3'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t3.txt
L_G = spconvert(L_SPA_t3);

disp 'L_SPA_t3'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t3.txt
L_G = spconvert(L_FTSPA_t3);

disp 'L_FTSPA_t3'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=4
load L_G_SPA_t4.txt
L_G = spconvert(L_G_SPA_t4);

disp 'L_G_SPA_t4'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t4.txt
L_G = spconvert(L_SPA_t4);

disp 'L_SPA_t4'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t4.txt
L_G = spconvert(L_FTSPA_t4);

disp 'L_FTSPA_t4'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=5
load L_G_SPA_t5.txt
L_G = spconvert(L_G_SPA_t5);

disp 'L_G_SPA_t5'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t5.txt
L_G = spconvert(L_SPA_t5);

disp 'L_SPA_t5'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t5.txt
L_G = spconvert(L_FTSPA_t5);

disp 'L_FTSPA_t5'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=6
load L_G_SPA_t6.txt
L_G = spconvert(L_G_SPA_t6);

disp 'L_G_SPA_t6'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t6.txt
L_G = spconvert(L_SPA_t6);

disp 'L_SPA_t6'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t6.txt
L_G = spconvert(L_FTSPA_t6);

disp 'L_FTSPA_t6'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=7
load L_G_SPA_t7.txt
L_G = spconvert(L_G_SPA_t7);

disp 'L_G_SPA_t7'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t7.txt
L_G = spconvert(L_SPA_t7);

disp 'L_SPA_t7'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t7.txt
L_G = spconvert(L_FTSPA_t7);

disp 'L_FTSPA_t7'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=8
load L_G_SPA_t8.txt
L_G = spconvert(L_G_SPA_t8);

disp 'L_G_SPA_t8'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t8.txt
L_G = spconvert(L_SPA_t8);

disp 'L_SPA_t8'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t8.txt
L_G = spconvert(L_FTSPA_t8);

disp 'L_FTSPA_t8'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=9
load L_G_SPA_t9.txt
L_G = spconvert(L_G_SPA_t9);

disp 'L_G_SPA_t9'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t9.txt
L_G = spconvert(L_SPA_t9);

disp 'L_SPA_t9'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t9.txt
L_G = spconvert(L_FTSPA_t9);

disp 'L_FTSPA_t9'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

%t=10
load L_G_SPA_t10.txt
L_G = spconvert(L_G_SPA_t10);

disp 'L_G_SPA_t10'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_SPA_t10.txt
L_G = spconvert(L_SPA_t10);

disp 'L_SPA_t10'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

load L_FTSPA_t10.txt
L_G = spconvert(L_FTSPA_t10);

disp 'L_FTSPA_t10'
ssl_mse(lambda,L_G,l,I_S,y,y_S);

diary off