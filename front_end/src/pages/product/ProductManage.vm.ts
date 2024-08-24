import { useNavigate } from 'react-router-dom'
import useGetAllApi from '@/hooks/use-get-all-api'
import { ProductResponse } from '@/model/Product'
import ProductConfigs from '@/pages/product/ProductConfigs'
import { TableRowSelection } from 'antd/es/table/interface'
import { useState } from 'react'
import { getProductColumns } from './ProductColumns'
function useProductManageViewModel() {
    const navigate = useNavigate()
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])

    // GET COLUMNS
    const columns = getProductColumns()

    // HANDLE SELECT CHANGE
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const { data: listResponse } = useGetAllApi<ProductResponse>(ProductConfigs.resourceUrl, ProductConfigs.resourceKey)

    const dataSource = listResponse?.items || []

    const handleRowClick = (record: ProductResponse) => {
        navigate(`/admin/products/${record.id}`)
    }

    const rowSelection: TableRowSelection<ProductResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    return { dataSource, handleRowClick, columns, rowSelection }
}
export default useProductManageViewModel
