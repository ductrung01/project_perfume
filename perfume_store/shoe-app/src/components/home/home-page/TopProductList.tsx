import React, { useEffect, useState } from 'react';
import DiscountLabel from '../../common/DiscountLabel';
import { CgDetailsMore } from "react-icons/cg";
import { FaCartPlus } from 'react-icons/fa6';
import { Variant } from '../../../models/Variant';
import { getTopSellingProducts } from '../../../services/product.service';
import { useNavigate } from 'react-router-dom';
import { addToCart } from '../../../services/cart.service';
import { toast, ToastContainer } from 'react-toastify';
import ProductDialog from '../product-page/ProductDialog';
import { useCart } from '../../../contexts/CartContext';
import { isAuthenticated } from '../../../services/auth.service';
import Swal from 'sweetalert2';
import { motion } from 'framer-motion';

const TopProductList: React.FC = () => {
    const [hoveredProductId, setHoveredProductId] = useState<number | null>(null);
    const [products, setProducts] = useState<Variant[]>([]);
    const [isOpenProductDialog, setOpenProductDialog] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState<any>(null);
    const { addItemToCart } = useCart();
    const [padding, setPadding] = useState(0);

    const navigate = useNavigate();

    const getAllProductVariant = async () => {
        const response = await getTopSellingProducts();
        console.log(response.data);

        setProducts(response.data);
    }

    const addProductToCart = async (productVariantId: number, e: React.MouseEvent) => {
        e.stopPropagation();

        if (isAuthenticated()) {
            const response = await addToCart(productVariantId, 1);
            if (response) {
                await addItemToCart();
                toast.success('Thêm vào giỏ hàng thành công');
            }
        } else {
            handleCloseProductDialog();
            Swal.fire({
                title: 'Vui lòng đăng nhập',
                text: 'Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng',
                icon: 'info',
                showCancelButton: true,
                confirmButtonText: 'Đăng nhập',
                cancelButtonText: 'Hủy',
            }).then((result) => {
                if (result.isConfirmed) {
                    navigate('/login');
                }
            });
        }
    };

    const handleOpenProductDialog = (product: Variant, e: React.MouseEvent) => {
        e.stopPropagation();
        setSelectedProduct(product);
        setOpenProductDialog(true);
    };

    const handleCloseProductDialog = () => {
        setOpenProductDialog(false);
        console.log('close dialog', isOpenProductDialog);
    };

    const handleChangePadding = () => {
        if (window.innerWidth <= 640) {
            setPadding(0);
        } else if (window.innerWidth <= 768) {
            setPadding(8);
        } else if (window.innerWidth <= 1024) {
            setPadding(16);
        } else {
            setPadding(20);
        }
    };

    const container = {
        hidden: { opacity: 0 },
        show: {
            opacity: 1,
            transition: {
                staggerChildren: 0.1
            }
        }
    };

    const item = {
        hidden: { opacity: 0, y: 20 },
        show: { opacity: 1, y: 0 }
    };

    useEffect(() => {
        getAllProductVariant();
        handleChangePadding();
        window.addEventListener('resize', handleChangePadding);
        return () => window.removeEventListener('resize', handleChangePadding);
    }, []);

    return (
        <section className="py-12 px-4">
            <h2 className="text-2xl font-bold mb-4">SẢN PHẨM BÁN CHẠY</h2>
            <div className="container mx-auto">                <motion.div
                variants={container}
                initial="hidden"
                animate="show"
                className={`grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 px-${padding} hover:cursor-pointer`}
            >
                {products.map((product) => (
                    <motion.div
                        key={product.id}
                        variants={item}
                        className="bg-white rounded-xl overflow-hidden shadow-md hover:shadow-xl transition-all duration-300 relative group"
                        whileHover={{ y: -5 }}
                        onClick={() => navigate(`/product-detail/${product.id}`)}
                    >
                        <div className="relative overflow-hidden">
                            <img
                                src={`${process.env.REACT_APP_BASE_URL}/files/preview/${product.imageAvatar}`}
                                alt={product.product.name}
                                className="w-full h-64 object-cover transition-transform duration-500 group-hover:scale-105"
                            />
                            <div className="absolute inset-0 bg-black bg-opacity-10 opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                        </div>

                        {product.price !== product.priceAfterDiscount && (
                            <DiscountLabel discount={product.discountRate} />
                        )}

                        <div className="p-4">
                            <h3 className="text-lg font-semibold text-gray-800 line-clamp-2 min-h-[3rem]">
                                {product.product.name}
                            </h3>
                            <div className="mt-4">
                                {product.discountRate > 0 ? (
                                    <div className="flex flex-col justify-end">
                                        <span className="text-gray-400 line-through text-end">
                                            {product.price.toLocaleString()} VNĐ
                                        </span>
                                        <span className="text-red-600 font-bold text-end text-lg">
                                            {product.priceAfterDiscount.toLocaleString()} VNĐ
                                        </span>
                                    </div>
                                ) : (
                                    <div className="flex flex-col justify-end">
                                        <span className="text-gray-700 font-semibold text-end text-lg mt-5">
                                            {product.price.toLocaleString()} VNĐ
                                        </span>
                                    </div>
                                )}
                            </div>
                        </div>

                        <div className="absolute top-4 right-4 flex flex-col gap-2 opacity-0 scale-0 group-hover:opacity-100 group-hover:scale-100 transform transition-all duration-200">
                            <motion.button
                                whileHover={{ scale: 1.1 }}
                                whileTap={{ scale: 0.95 }}
                                className="bg-white p-2.5 rounded-full shadow-lg hover:bg-blue-50 text-blue-600"
                                onClick={(e) => addProductToCart(product.id, e)}
                            >
                                <FaCartPlus size={20} />
                            </motion.button>

                            <motion.button
                                whileHover={{ scale: 1.1 }}
                                whileTap={{ scale: 0.95 }}
                                className="bg-white p-2.5 rounded-full shadow-lg hover:bg-blue-50 text-blue-600"
                                onClick={(e) => handleOpenProductDialog(product, e)}
                            >
                                <CgDetailsMore size={20} />
                            </motion.button>
                        </div>
                    </motion.div>
                ))}
            </motion.div>
            </div>
            <ToastContainer />
        </section>
    );
};

export default TopProductList;