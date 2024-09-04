import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductResponse } from '@/model/Product'
import ProductConfigs from '@/pages/product/ProductConfigs'
import { TableRowSelection } from 'antd/es/table/interface'
import { useState } from 'react'
import { getProductColumns } from './ProductColumns'
import { ProductFilter } from '@/model/ProductFilter'

function useProductManageViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
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

    const rowSelection: TableRowSelection<ProductResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    return { dataSource, columns, rowSelection, handleFilterChange }
}

export default useProductManageViewModel
