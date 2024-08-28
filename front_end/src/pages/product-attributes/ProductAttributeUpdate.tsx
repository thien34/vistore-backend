import { useEffect } from 'react'
import { Form, Input, Button, Space, Modal, Table, Empty, Collapse, Checkbox, InputNumber } from 'antd'
import { CheckCircleOutlined, DeleteOutlined, EditOutlined, PlusOutlined } from '@ant-design/icons'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import useProductAttributeUpdate from '@/pages/product-attributes/ProductAttributeUpdate.vm.ts'
import TextArea from 'antd/es/input/TextArea'

const pageSize = 6

export default function ProductAttributeUpdate() {
    const {
        productAttributeResponse,
        isLoading,
        current,
        handlePageChange,
        form,
        formAdd,
        values,
        setValues,
        newValue,
        setNewValue,
        isModalOpen,
        setIsModalOpen,
        handleAddValue,
        handleRemoveValue,
        handleEditValue,
        handleFinish,
        handleInputChange,
        handleModalCancel,
        loading,
    } = useProductAttributeUpdate()

    useEffect(() => {
        if (productAttributeResponse) {
            form.setFieldsValue({
                name: productAttributeResponse.name,
                description: productAttributeResponse.description,
            })
            setValues(productAttributeResponse.values)
        }
    }, [form, productAttributeResponse, setValues])

    const columns = [
        { title: 'Name', dataIndex: 'name', key: 'name' },
        {
            title: 'Price Adjustment',
            dataIndex: 'priceAdjustment',
            key: 'priceAdjustment',
            render: (text: number, record: PredefinedProductAttributeValueRequest) => (
                <>{record.priceAdjustmentUsePercentage ? `${text}%` : `$${text}`}</>
            ),
        },
        {
            title: 'Price Adjustment Use Percentage',
            dataIndex: 'priceAdjustmentUsePercentage',
            key: 'priceAdjustmentUsePercentage',
            render: (text: boolean) => (text ? '✔' : '✘'),
        },
        { title: 'Weight Adjustment', dataIndex: 'weightAdjustment', key: 'weightAdjustment' },
        { title: 'Cost', dataIndex: 'cost', key: 'cost' },
        {
            title: 'Pre-selected',
            dataIndex: 'isPreSelected',
            key: 'isPreSelected',
            render: (text: boolean) => (text ? '✔' : '✘'),
        },
        { title: 'Display Order', dataIndex: 'displayOrder', key: 'displayOrder' },
        {
            title: 'Action',
            key: 'action',
            render: (record: PredefinedProductAttributeValueRequest) => (
                <Space size='middle'>
                    <Button
                        type='default'
                        style={{
                            backgroundColor: '#4682B4',
                            color: 'white',
                            borderColor: 'white',
                            borderWidth: 1,
                            borderStyle: 'solid',
                        }}
                        icon={<EditOutlined />}
                        onClick={() => handleEditValue(record)}
                    >
                        Edit
                    </Button>

                    <Button
                        type='link'
                        style={{
                            backgroundColor: '#FF0000',
                            color: '#FFFFFF',
                        }}
                        icon={<DeleteOutlined />}
                        onClick={() =>
                            Modal.confirm({
                                title: 'Confirm Delete',
                                content: 'Are you sure you want to delete this value?',
                                okText: 'Yes',
                                cancelText: 'No',
                                onOk: () => handleRemoveValue(record),
                            })
                        }
                    >
                        Remove
                    </Button>
                </Space>
            ),
        },
    ]

    if (isLoading && !productAttributeResponse) {
        return <div>Loading...</div>
    }

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <Form id='myform1' form={form} onFinish={handleFinish} layout='vertical' style={{ width: '100%' }}>
                <Collapse defaultActiveKey={['1', '2']} style={{ width: '100%' }}>
                    <Collapse.Panel header='Attribute Information' key='1'>
                        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                            <div style={{ width: '66.67%' }}>
                                <Form.Item
                                    name='name'
                                    label='Name'
                                    rules={[{ required: true, message: 'Please enter the attribute name' }]}
                                >
                                    <Input size='large' placeholder='Enter attribute name' style={{ width: '100%' }} />
                                </Form.Item>
                                <Form.Item name='description' label='Description'>
                                    <TextArea
                                        size='large'
                                        rows={3}
                                        placeholder='Enter description'
                                        style={{ width: '100%' }}
                                    />
                                </Form.Item>
                                <Form.Item className='mt-5' style={{ display: 'flex', justifyContent: 'center' }}>
                                    <Button
                                        type='primary'
                                        style={{
                                            backgroundColor: '#34C759',
                                            borderColor: '#34C759',
                                        }}
                                        onClick={() => {
                                            Modal.confirm({
                                                title: 'Confirm Save',
                                                content: 'Are you sure you want to save the changes?',
                                                okText: 'Yes',
                                                cancelText: 'No',
                                                onOk: () => handleFinish(form.getFieldsValue()),
                                            })
                                        }}
                                        icon={<CheckCircleOutlined />}
                                        loading={loading}
                                    >
                                        Save
                                    </Button>
                                </Form.Item>
                            </div>
                        </div>
                    </Collapse.Panel>
                    <Collapse.Panel header='Predefined Values' key='2'>
                        <Space direction='vertical' style={{ width: '100%' }}>
                            <Button type='dashed' onClick={() => setIsModalOpen(true)} icon={<PlusOutlined />}>
                                Add New Value
                            </Button>
                            {values.length === 0 ? (
                                <Empty description='No data available in table' />
                            ) : (
                                <Table
                                    dataSource={values}
                                    columns={columns}
                                    pagination={{
                                        current,
                                        pageSize,
                                        total: values.length,
                                        onChange: (page) => handlePageChange(page),
                                    }}
                                    scroll={{ x: 1000 }}
                                    rowKey='id'
                                />
                            )}
                        </Space>
                    </Collapse.Panel>
                </Collapse>
            </Form>

            <Modal
                title='Edit Predefined Value'
                open={isModalOpen}
                centered
                onOk={formAdd.submit}
                onCancel={handleModalCancel}
            >
                <Form form={formAdd} layout='vertical' onFinish={handleAddValue}>
                    <Form.Item
                        name='name'
                        label={<span style={{ fontWeight: 'bold' }}>Name</span>}
                        rules={[{ required: true, message: 'Please enter the name' }]}
                    >
                        <Input
                            value={newValue.name}
                            onChange={(e) => setNewValue({ ...newValue, name: e.target.value })}
                            placeholder='Enter name'
                        />
                    </Form.Item>
                    <Form.Item
                        name='priceAdjustment'
                        label={<span style={{ fontWeight: 'bold' }}>Price Adjustment</span>}
                    >
                        <Input
                            addonAfter={newValue.priceAdjustmentUsePercentage ? '%' : '$'}
                            value={newValue.priceAdjustment || 0}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    priceAdjustment: e.target.value.replace(/[^\d.-]/g, '')
                                        ? parseFloat(e.target.value.replace(/[^\d.-]/g, ''))
                                        : 0,
                                })
                            }
                            placeholder='Enter price adjustment'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item label={<span style={{ fontWeight: 'bold' }}>Price Adjustment Use Percentage</span>}>
                        <Checkbox
                            checked={newValue.priceAdjustmentUsePercentage}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    priceAdjustmentUsePercentage: e.target.checked,
                                })
                            }
                        />
                    </Form.Item>
                    <Form.Item
                        name='weightAdjustment'
                        label={<span style={{ fontWeight: 'bold' }}>Weight Adjustment</span>}
                    >
                        <Input
                            addonAfter={'$'}
                            value={newValue.weightAdjustment || 0}
                            onChange={handleInputChange('weightAdjustment')}
                            placeholder='Enter weight adjustment'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item name='cost' label={<span style={{ fontWeight: 'bold' }}>Cost</span>}>
                        <Input
                            addonAfter={'$'}
                            value={newValue.cost || 0}
                            onChange={handleInputChange('cost')}
                            placeholder='Enter cost'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item label={<span style={{ fontWeight: 'bold' }}>Is Pre-selected</span>}>
                        <Checkbox
                            checked={newValue.isPreSelected}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    isPreSelected: e.target.checked,
                                })
                            }
                        />
                    </Form.Item>
                    <Form.Item name='displayOrder' label={<span style={{ fontWeight: 'bold' }}>Display Order</span>}>
                        <InputNumber
                            value={newValue.displayOrder || 0}
                            onChange={(value) =>
                                setNewValue({
                                    ...newValue,
                                    displayOrder: value ?? 0,
                                })
                            }
                            placeholder='Enter display order'
                            style={{ width: '100%' }}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}
