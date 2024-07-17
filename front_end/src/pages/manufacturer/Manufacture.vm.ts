import useDeleteByIdsApi from "@/hooks/use-delete-by-ids-api"
import { RequestParams } from "@/utils/FetchUtils"
import { useState } from "react"
import ManufactureConfigs from "./ManufactureConfigs"
import getManufactureColumns from "./ManufactureColumns"
import { ManufacturerResponse } from "@/model/Manufacturer"
import { TableRowSelection } from "antd/es/table/interface"
import useGetAllApi from "@/hooks/use-get-all-api"

interface Search extends RequestParams {
    published?: boolean
    page?: number
}

export default function useManufactureViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [filter, setFilter] = useState<Search>({})
    const { mutate: deleteApi } = useDeleteByIdsApi<number>(ManufactureConfigs.resourceUrl, ManufactureConfigs.resourceKey)

    // GET COLUMNS
    const columns = getManufactureColumns()

    // HANDLE SELECT CHANGE
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const rowSelection: TableRowSelection<ManufacturerResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    // RETURN DATA
    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<ManufacturerResponse>(ManufactureConfigs.resourceUrl, ManufactureConfigs.resourceKey, filter)

    // HANDLE DELETE
    const handleDelete = async () => {
        deleteApi(selectedRowKeys as number[], {
            onSuccess: () => {
                refetch()
                setSelectedRowKeys([])
            },
        })
    }

    // HANDLE TABLE CHANGE
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            page: pagination.current,
        }))
    }

    // HANDLE SEARCH
    const handleSearch = (newFilter : { name: string; published: boolean | undefined }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            ...newFilter,
            page: 1,
        }))
    }

    return {
        rowSelection,
        columns,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        isLoading,
    }
}