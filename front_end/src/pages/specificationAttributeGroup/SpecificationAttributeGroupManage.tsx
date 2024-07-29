import React, { useState } from 'react'
import { Table, Button, Row, Col, Space, Modal } from 'antd'
import { DeleteOutlined, EditOutlined, PlusOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import useGetAllApi from '@/hooks/use-get-all-api.ts'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api.ts'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import { TableRowSelection } from 'antd/es/table/interface'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'

const { Column } = Table
const { confirm } = Modal

export default function SpecificationAttributeGroupManage() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])

    // Fetch grouped attributes
    const { data, isLoading, error, refetch } = useGetAllApi(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
    )

    const { mutate: deleteApi } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
    )

    // Fetch ungrouped attributes
    const {
        data: ungroupedAttributesData,
        isLoading: isLoadingUngrouped,
        error: errorUngrouped,
    } = useGetAllApi(
        SpecificationAttributeGroupConfigs.resourceGetUngroupedAttributes,
        SpecificationAttributeGroupConfigs.resourceGetUngroupedAttributesKey,
    )

    const handleEdit = (record) => {
        console.log(`Edit attribute: ${record.name}`)
    }

    if (isLoading || isLoadingUngrouped) {
        return <div>Loading...</div>
    }

    if (error || errorUngrouped) {
        return <div>Error loading data: {error?.message || errorUngrouped?.message}</div>
    }

    // Combine ungrouped attributes into the data source
    const ungroupedAttributes = ungroupedAttributesData?.items || []
    const dataSource = [
        {
            id: 'ungrouped',
            name: 'Default group (non-grouped specification attributes)',
            displayOrder: 0,
            specificationAttributes: ungroupedAttributes,
        },
        ...data?.items,
    ]

    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const handleDelete = () => {
        confirm({
            title: 'Are you sure you want to delete the selected attributes?',
            content: 'This action cannot be undone.',
            onOk: async () => {
                deleteApi(selectedRowKeys as number[], {
                    onSuccess: () => {
                        refetch()
                        setSelectedRowKeys([])
                    },
                })
            },
            onCancel() {
                console.log('Cancel')
            },
        })
    }

    const rowSelection: TableRowSelection<SpecificationAttributeResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    return (
        <div>
            <Row justify='space-between' align='middle' style={{ marginBottom: 16 }}>
                <Col>
                    <h1>Specification attributes</h1>
                </Col>
                <Col>
                    <Space>
                        <Link to='/admin/specification-attribute-groups/add'>
                            <Button icon={<PlusOutlined />} className='bg-[#475569] text-white' size='middle'>
                                Add Group
                            </Button>
                        </Link>
                        <Link to='/admin/specification-attributes/add'>
                            <Button icon={<PlusOutlined />} className='bg-[#475569] text-white' size='middle'>
                                Add Attribute
                            </Button>
                        </Link>
                        <Button
                            type='primary'
                            danger
                            icon={<DeleteOutlined />}
                            onClick={handleDelete}
                            disabled={selectedRowKeys.length === 0}
                        >
                            Delete attributes (selected)
                        </Button>
                    </Space>
                </Col>
            </Row>
            <Table
                dataSource={dataSource}
                expandable={{
                    expandedRowRender: (record) => (
                        <Table
                            dataSource={record.specificationAttributes}
                            pagination={{ pageSize: 6 }}
                            rowKey='id'
                            rowSelection={{
                                type: 'checkbox',
                                onChange: (newSelectedRowKeys) => {
                                    setSelectedRowKeys((prevKeys) => [...prevKeys, ...newSelectedRowKeys])
                                },
                            }}
                        >
                            <Column title='Name' dataIndex='name' key='name' />
                            <Column title='Display order' dataIndex='displayOrder' key='displayOrder' />
                            <Column
                                title='Edit'
                                key='edit'
                                render={(text, record) => (
                                    <Button
                                        type='primary'
                                        icon={<EditOutlined />}
                                        size='middle'
                                        onClick={() => handleEdit(record)}
                                    >
                                        Edit
                                    </Button>
                                )}
                            />
                        </Table>
                    ),
                }}
                rowKey='id'
                pagination={{ pageSize: 6 }}
            >
                <Column title='Name' dataIndex='name' key='name' />
                <Column title='Display order' dataIndex='displayOrder' key='displayOrder' />
            </Table>
        </div>
    )
}
