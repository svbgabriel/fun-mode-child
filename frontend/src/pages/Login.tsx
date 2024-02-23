import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useNavigate, Link } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';
import api from '../services/api';
import { Mail, Lock, LogIn, ArrowRight } from 'lucide-react';

const loginSchema = z.object({
    email: z.string().email('E-mail inválido'),
    password: z.string().min(6, 'A senha deve ter pelo menos 6 caracteres'),
});

type LoginFormValues = z.infer<typeof loginSchema>;

const Login: React.FC = () => {
    const navigate = useNavigate();
    const { setAuth } = useAuthStore();
    const [error, setError] = React.useState<string | null>(null);
    const [loading, setLoading] = React.useState(false);

    const { register, handleSubmit, formState: { errors } } = useForm<LoginFormValues>({
        resolver: zodResolver(loginSchema),
    });

    const onSubmit = async (data: LoginFormValues) => {
        setLoading(true);
        setError(null);
        try {
            const response = await api.post('/users/login', { username: data.email, password: data.password });
            const { accessToken, user } = response.data;
            setAuth(user, accessToken);
            navigate('/');
        } catch (err: any) {
            setError(err.response?.data?.message || 'Erro ao fazer login. Verifique suas credenciais.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-[80vh] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-gray-50">
            <div className="max-w-md w-full bg-white rounded-3xl shadow-2xl overflow-hidden p-8 border border-gray-100">
                <div className="text-center mb-10">
                    <div className="inline-flex items-center justify-center p-4 bg-primary-100 rounded-2xl text-primary-600 mb-4">
                        <LogIn className="w-10 h-10" />
                    </div>
                    <h2 className="text-3xl font-black text-gray-900">Bem-vindo de volta!</h2>
                    <p className="mt-2 text-gray-500 font-medium">Faça login para continuar suas compras</p>
                </div>

                {error && (
                    <div className="mb-6 p-4 bg-red-50 border-l-4 border-red-500 text-red-700 text-sm font-medium rounded-r-lg">
                        {error}
                    </div>
                )}

                <form className="space-y-6" onSubmit={handleSubmit(onSubmit)}>
                    <div>
                        <label className="block text-sm font-bold text-gray-700 mb-2">E-mail</label>
                        <div className="relative">
                            <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                                <Mail className="w-5 h-5" />
                            </span>
                            <input
                                {...register('email')}
                                type="email"
                                className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${errors.email ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                                    }`}
                                placeholder="seu@email.com"
                            />
                        </div>
                        {errors.email && <p className="mt-2 text-sm text-red-600 font-medium">{errors.email.message}</p>}
                    </div>

                    <div>
                        <label className="block text-sm font-bold text-gray-700 mb-2">Senha</label>
                        <div className="relative">
                            <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                                <Lock className="w-5 h-5" />
                            </span>
                            <input
                                {...register('password')}
                                type="password"
                                className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${errors.password ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                                    }`}
                                placeholder="••••••••"
                            />
                        </div>
                        {errors.password && <p className="mt-2 text-sm text-red-600 font-medium">{errors.password.message}</p>}
                    </div>

                    <div className="flex items-center justify-between">
                        <div className="flex items-center">
                            <input
                                id="remember-me"
                                type="checkbox"
                                className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded cursor-pointer"
                            />
                            <label htmlFor="remember-me" className="ml-2 block text-sm text-gray-600 cursor-pointer">
                                Lembrar de mim
                            </label>
                        </div>
                        <div className="text-sm">
                            <a href="#" className="font-bold text-primary-600 hover:underline">
                                Esqueceu sua senha?
                            </a>
                        </div>
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full flex justify-center items-center py-4 px-6 bg-primary-600 hover:bg-primary-700 text-white text-lg font-bold rounded-2xl shadow-lg shadow-primary-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 transition-all disabled:opacity-50"
                    >
                        {loading ? 'Entrando...' : (
                            <>
                                Entrar <ArrowRight className="ml-2 w-5 h-5" />
                            </>
                        )}
                    </button>
                </form>

                <div className="mt-10 pt-8 border-t border-gray-100 text-center">
                    <p className="text-gray-600 font-medium">
                        Não tem uma conta?{' '}
                        <Link to="/register" className="text-primary-600 font-bold hover:underline">
                            Cadastre-se grátis
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Login;
