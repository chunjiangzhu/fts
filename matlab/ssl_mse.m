function [mse] = ssl_mse(lambda,L_G,l,I_S,y,y_S)
tic
tilde_f = (lambda*l*L_G+I_S)\y_S;
toc

mse = immse(tilde_f, y);
fprintf('The mean-squared error is %0.8f\n\n', mse);
end