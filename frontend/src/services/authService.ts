import { apiRequest } from './api';
import type { ApiResponse, AuthResponse, LoginRequest, RegisterRequest } from '@/types';

export const authService = {
  login: (credentials: LoginRequest) =>
    apiRequest.post<ApiResponse<AuthResponse>>('/auth/login', credentials),

  register: (data: RegisterRequest) =>
    apiRequest.post<ApiResponse<AuthResponse>>('/auth/register', data),

  refreshToken: (refreshToken: string) =>
    apiRequest.post<ApiResponse<AuthResponse>>('/auth/refresh', { refreshToken }),

  logout: (userId: number) =>
    apiRequest.post<ApiResponse<null>>('/auth/logout', null, { params: { userId } }),

  forgotPassword: (email: string) =>
    apiRequest.post<ApiResponse<null>>('/auth/forgot-password', { email }),

  resetPassword: (token: string, newPassword: string) =>
    apiRequest.post<ApiResponse<null>>('/auth/reset-password', { token, newPassword }),
};
