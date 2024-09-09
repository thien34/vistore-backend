import { useNavigate } from 'react-router-dom'
import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductDetailResponse, ProductResponse } from '@/model/Product'
import ProductConfigs from '@/pages/product/ProductConfigs'
import { TableRowSelection } from 'antd/es/table/interface'
import { useState } from 'react'
import { getProductColumns } from './ProductColumns'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import { useDispatch } from 'react-redux'
import { setProduct } from '@/slice/productSlice'
import { useQueryClient } from '@tanstack/react-query'

import { ProductFilter } from '@/model/ProductFilter'

function useProductManageViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [productId, setProductId] = useState<number | null>(null)
    const dispatch = useDispatch()
    const queryClient = useQueryClient()
    const navigate = useNavigate()
    const [filter, setFilter] = useState<ProductFilter>({})

    // GET COLUMNS
    const columns = getProductColumns()

    // HANDLE SELECT CHANGE
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const handleFilterChange = (newFilter: ProductFilter) => {
        setFilter(newFilter)
    }

    const { data: listResponse } = useGetAllApi<ProductResponse>(
        ProductConfigs.resourceUrl,
        ProductConfigs.resourceKey,
        filter,
    )

    const dataSource = listResponse?.items || []

    const { refetch: fetchProductDetail } = useGetByIdApi<ProductDetailResponse>(
        ProductConfigs.resourceUrl,
        ProductConfigs.resourceKey,
        productId,
    )

    const handleRowClick = async (record: ProductResponse) => {
        setProductId(record.id)
        await queryClient.invalidateQueries({ queryKey: ['product', record.id] })
        const { data: productDetail } = await fetchProductDetail()

        if (productDetail) {
            dispatch(setProduct(productDetail))
            navigate(`/admin/products/${record.id}`)
        }
    }

    const rowSelection: TableRowSelection<ProductResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    return { dataSource, columns, rowSelection, handleFilterChange, handleRowClick }
}

export default useProductManageViewModel
