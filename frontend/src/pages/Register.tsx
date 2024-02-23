import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useNavigate, Link } from 'react-router-dom';
import api from '../services/api';
import { UserPlus, Mail, Lock, User, Calendar, IdCard, Phone, ArrowRight } from 'lucide-react';

const registerSchema = z.object({
    name: z.string().min(2, 'Nome deve ter pelo menos 2 caracteres'),
    surname: z.string().min(2, 'Sobrenome deve ter pelo menos 2 caracteres'),
    birthDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Data de nascimento deve estar no formato YYYY-MM-DD'),
    email: z.string().email('E-mail inválido'),
    customerIdentification: z.string().min(1, 'Identificação (CPF/RG) é obrigatória'),
    phoneNumber: z.string().min(1, 'Telefone é obrigatório'),
    password: z.string().min(6, 'A senha deve ter pelo menos 6 caracteres'),
    passwordConfirm: z.string()
}).refine((data) => data.password === data.passwordConfirm, {
    message: "As senhas não coincidem",
    path: ["passwordConfirm"],
});

type RegisterFormValues = z.infer<typeof registerSchema>;

const Register: React.FC = () => {
    const navigate = useNavigate();
    const [error, setError] = React.useState<string | null>(null);
    const [loading, setLoading] = React.useState(false);

    const { register, handleSubmit, formState: { errors } } = useForm<RegisterFormValues>({
        resolver: zodResolver(registerSchema),
    });

    const onSubmit = async (data: RegisterFormValues) => {
        setLoading(true);
        setError(null);
        try {
            // Address is optional in the backend UserRequest, so we send null or empty Address
            await api.post('/users', {
                name: data.name,
                surname: data.surname,
                birthDate: data.birthDate,
                email: data.email,
                customerIdentification: data.customerIdentification,
                phoneNumber: data.phoneNumber,
                password: data.password,
                address: null
            });
            navigate('/login');
        } catch (err: any) {
            setError(err.response?.data?.message || 'Erro ao realizar cadastro. Tente novamente mais tarde.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-[80vh] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-gray-50">
            <div className="max-w-2xl w-full bg-white rounded-3xl shadow-2xl overflow-hidden p-8 md:p-12 border border-gray-100">
                <div className="text-center mb-10">
                    <div className="inline-flex items-center justify-center p-4 bg-primary-100 rounded-2xl text-primary-600 mb-4">
                        <UserPlus className="w-10 h-10" />
                    </div>
                    <h2 className="text-3xl font-black text-gray-900">Crie sua conta</h2>
                    <p className="mt-2 text-gray-500 font-medium">Junte-se à diversão na FunModeChild</p>
                </div>

                {error && (
                    <div className="mb-6 p-4 bg-red-50 border-l-4 border-red-500 text-red-700 text-sm font-medium rounded-r-lg">
                        {error}
                    </div>
                )}

                <form className="space-y-6" onSubmit={handleSubmit(onSubmit)}>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label className="block text-sm font-bold text-gray-700 mb-2">Nome</label>
                            <div className="relative">
                                <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                                    <User className="w-5 h-5" />
                                </span>
                                <input
                                    {...register('name')}
                                    type="text"
                                    className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${errors.name ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                                        }`}
                                    placeholder="Seu nome"
                                />
                            </div>
                            {errors.name && <p className="mt-2 text-sm text-red-600 font-medium">{errors.name.message}</p>}
                        </div>

                        <div>
                            <label className="block text-sm font-bold text-gray-700 mb-2">Sobrenome</label>
                            <div className="relative">
                                <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                                    <User className="w-5 h-5" />
                                </span>
                                <input
                                    {...register('surname')}
                                    type="text"
                                    className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${errors.surname ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                                        }`}
                                    placeholder="Seu sobrenome"
                                />
                            </div>
                            {errors.surname && <p className="mt-2 text-sm text-red-600 font-medium">{errors.surname.message}</p>}
                        </div>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label className="block text-sm font-bold text-gray-700 mb-2">Data de Nascimento</label>
                            <div className="relative">
                                <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                                    <Calendar className="w-5 h-5" />
                                </span>
                                <input
                                    {...register('birthDate')}
                                    type="date"
                                    className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${errors.birthDate ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                                        }`}
                                />
                            </div>
                            {errors.birthDate && <p className="mt-2 text-sm text-red-600 font-medium">{errors.birthDate.message}</p>}
                        </div>

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
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label className="block text-sm font-bold text-gray-700 mb-2">Identificação (CPF/RG)</label>
                            <div className="relative">
                                <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                                    <IdCard className="w-5 h-5" />
                                </span>
                                <input
                                    {...register('customerIdentification')}
                                    type="text"
                                    className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${errors.customerIdentification ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                                        }`}
                                    placeholder="000.000.000-00"
                                />
                            </div>
                            {errors.customerIdentification && <p className="mt-2 text-sm text-red-600 font-medium">{errors.customerIdentification.message}</p>}
                        </div>

                        <div>
                            <label className="block text-sm font-bold text-gray-700 mb-2">Telefone</label>
                            <div className="relative">
                                <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                                    <Phone className="w-5 h-5" />
                                </span>
                                <input
                                    {...register('phoneNumber')}
                                    type="text"
                                    className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${errors.phoneNumber ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                                        }`}
                                    placeholder="(00) 00000-0000"
                                />
                            </div>
                            {errors.phoneNumber && <p className="mt-2 text-sm text-red-600 font-medium">{errors.phoneNumber.message}</p>}
                        </div>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
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

                        <div>
                            <label className="block text-sm font-bold text-gray-700 mb-2">Confirmar Senha</label>
                            <div className="relative">
                                <span className="absolute inset-y-0 left-0 pl-4 flex items-center text-gray-400">
                                    <Lock className="w-5 h-5" />
                                </span>
                                <input
                                    {...register('passwordConfirm')}
                                    type="password"
                                    className={`block w-full pl-12 pr-4 py-4 border-2 rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary-500 transition-all ${errors.passwordConfirm ? 'border-red-300' : 'border-gray-100 hover:border-primary-300'
                                        }`}
                                    placeholder="••••••••"
                                />
                            </div>
                            {errors.passwordConfirm && <p className="mt-2 text-sm text-red-600 font-medium">{errors.passwordConfirm.message}</p>}
                        </div>
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full flex justify-center items-center py-4 px-6 bg-primary-600 hover:bg-primary-700 text-white text-lg font-bold rounded-2xl shadow-lg shadow-primary-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 transition-all disabled:opacity-50"
                    >
                        {loading ? 'Cadastrando...' : (
                            <>
                                Cadastrar <ArrowRight className="ml-2 w-5 h-5" />
                            </>
                        )}
                    </button>
                </form>

                <div className="mt-10 pt-8 border-t border-gray-100 text-center">
                    <p className="text-gray-600 font-medium">
                        Já tem uma conta?{' '}
                        <Link to="/login" className="text-primary-600 font-bold hover:underline">
                            Fazer login
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Register;
