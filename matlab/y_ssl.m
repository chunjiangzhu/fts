
% import y and signal corresponding to sd=0.1=10^{-1}
% load y.txt
% load signal.txt

% import y and signal corresponding to sd=0.01=10^{-2}
load y-2.txt
load signal-2.txt
y=y_2;
signal=signal_2;

len = length(y);
%#samples
l = 1000;

%get the sign of (y-1)
y = sign(y-1);
sampledOnes = datasample(find(y>0),l/2,'Replace',false);
sampledMinusOnes = datasample(find(y<0),l/2,'Replace',false);
samples = union(sampledOnes,sampledMinusOnes);
I_S = zeros(len);
y_S = zeros(len,1);
for i = 1:length(samples)
    j = samples(i);
    I_S(j,j)=1;
    y_S(j)=y(j);
end
clear samples
clear sampledMinusOnes
clear sampledOnes
clear i
clear j

% save data structures for ssl corresponding to sd=10^{-1}
% -1 is default and is ignored in the name
% save y_ssl

% save data structures for ssl corresponding to sd=10^{-2}
save y_ssl-2
