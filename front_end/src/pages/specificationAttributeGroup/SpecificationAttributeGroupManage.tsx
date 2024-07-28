import React, { useState } from 'react'
import { Button, Collapse, Table, Space, Modal, Form, Input, Pagination } from 'antd'
import { PlusOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'

const { Panel } = Collapse
const { Column } = Table

const outerData = [
    {
        key: '1',
        name: 'Default group (non-grouped specification attributes)',
        displayOrder: 0,
        innerData: [
            { key: '1', name: 'Screensize', displayOrder: 1 },
            { key: '2', name: 'Hardrive', displayOrder: 5 },
            { key: '3', name: 'RAM', displayOrder: 3 },
            { key: '4', name: 'CPU', displayOrder: 4 },
            { key: '5', name: 'GPU', displayOrder: 2 },
            { key: '6', name: 'Battery Life', displayOrder: 6 },
            { key: '7', name: 'Weight', displayOrder: 7 },
            { key: '8', name: 'Dimensions', displayOrder: 8 },
            { key: '9', name: 'Color', displayOrder: 9 },
            { key: '10', name: 'Material', displayOrder: 10 },
        ],
    },
    // Additional outerData items...
]

const outerPageSize = 1 // Outer panel page size
const innerPageSize = 5 // Inner table page size

const SpecificationAttributeGroupManage = () => {
    const [selectedRowKeys, setSelectedRowKeys] = useState([])
    const [isModalVisible, setIsModalVisible] = useState(false)
    const [form] = Form.useForm()
    const [outerCurrentPage, setOuterCurrentPage] = useState(1)
    const [innerCurrentPages, setInnerCurrentPages] = useState({}) // Track inner pages per panel

    const showDeleteConfirm = () => {
        Modal.confirm({
            title: 'Are you sure you want to delete the selected attributes?',
            content: 'This action cannot be undone.',
            okText: 'Yes',
            okType: 'danger',
            cancelText: 'No',
            onOk() {
                handleDelete()
            },
        })
    }

    const handleDelete = () => {
        console.log('Deleting attributes:', selectedRowKeys)
        // Implement delete logic here
    }

    const handleAddAttribute = () => {
        form.validateFields()
            .then((values) => {
                console.log('Adding attribute:', values)
                // Implement add logic here
                setIsModalVisible(false)
                form.resetFields()
            })
            .catch((errorInfo) => {
                console.log('Failed:', errorInfo)
            })
    }

    const handleCancel = () => {
        setIsModalVisible(false)
        form.resetFields()
    }

    const onSelectChange = (selectedRowKeys) => {
        setSelectedRowKeys(selectedRowKeys)
    }

    const rowSelection = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    const handleOuterPageChange = (page) => {
        setOuterCurrentPage(page)
    }

    const handleInnerPageChange = (outerKey, page) => {
        setInnerCurrentPages((prev) => ({ ...prev, [outerKey]: page }))
    }

    // Calculate the current outer data to be displayed based on pagination
    const outerPagedData = outerData.slice((outerCurrentPage - 1) * outerPageSize, outerCurrentPage * outerPageSize)

    return (
        <div style={{ padding: 24 }}>
            <b style={{ fontSize: 30 }}>Specification attributes</b>
            <p style={{ margin: 20 }}>
                Specification attributes are product features i.e., screen size, number of USB-ports, visible on product
                details page. Specification attributes can be used for filtering products on the category details page.
                Unlike product attributes, specification attributes are used for information purposes only. You can add
                attributes to existing product on a product details page.
            </p>

            <Space style={{ marginBottom: 16 }}>
                <Link to='/admin/specification-attribute-groups/add'>
                    <Button type='primary' icon={<PlusOutlined />}>
                        Add group
                    </Button>
                </Link>
                <Link to='/admin/specification-attributes/add/'>
                    <Button type='default' icon={<PlusOutlined />}>
                        Add attribute
                    </Button>
                </Link>
                <Button type='default' icon={<DeleteOutlined />} onClick={showDeleteConfirm}>
                    Delete attributes (selected)
                </Button>
            </Space>

            <Collapse defaultActiveKey={['1']}>
                {outerPagedData.map((outerItem) => (
                    <Panel header={outerItem.name} key={outerItem.key}>
                        <Table
                            dataSource={outerItem.innerData.slice(
                                ((innerCurrentPages[outerItem.key] || 1) - 1) * innerPageSize,
                                (innerCurrentPages[outerItem.key] || 1) * innerPageSize,
                            )}
                            pagination={false} // Disable built-in pagination
                            rowKey='key'
                            rowSelection={rowSelection}
                        >
                            <Column title='Name' dataIndex='name' key='name' />
                            <Column title='Display order' dataIndex='displayOrder' key='displayOrder' />
                            <Column
                                title='Edit'
                                key='edit'
                                render={(_, record) => (
                                    <Space size='middle'>
                                        <Link to={`/admin/specification-attributes/${record.key}/update`}>
                                            <Button icon={<EditOutlined />}>Edit</Button>
                                        </Link>
                                    </Space>
                                )}
                            />
                        </Table>
                        <Pagination
                            style={{ marginTop: 16 }}
                            current={innerCurrentPages[outerItem.key] || 1}
                            pageSize={innerPageSize}
                            total={outerItem.innerData.length}
                            onChange={(page) => handleInnerPageChange(outerItem.key, page)}
                        />
                    </Panel>
                ))}
            </Collapse>

            <Pagination
                style={{ marginTop: 16 }}
                current={outerCurrentPage}
                pageSize={outerPageSize}
                total={outerData.length}
                onChange={handleOuterPageChange}
            />

            <Modal
                title='Add Attribute'
                open={isModalVisible}
                onOk={handleAddAttribute}
                onCancel={handleCancel}
                okText='Add'
            >
                <Form form={form} layout='vertical'>
                    <Form.Item
                        name='name'
                        label='Name'
                        rules={[{ required: true, message: 'Please input the attribute name!' }]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name='displayOrder'
                        label='Display order'
                        rules={[{ required: true, message: 'Please input the display order!' }]}
                    >
                        <Input type='number' />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}

export default SpecificationAttributeGroupManage
