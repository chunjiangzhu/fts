function [mse] = lapsmo_mse(lambda,L_G,len,y,signal)
tic
tilde_f = (lambda*L_G+eye(len))\y;
toc

mse = immse(tilde_f, signal);
fprintf('The mean-squared error is %0.8f\n\n', mse);
end