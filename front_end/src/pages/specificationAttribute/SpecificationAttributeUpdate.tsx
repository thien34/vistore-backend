import { useEffect } from 'react'
import { Form, Input, Select, Button, Table, Modal, Checkbox, InputNumber, ColorPicker } from 'antd'
import { DeleteOutlined, SaveOutlined } from '@ant-design/icons'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import useSpecificationAttributeUpdateViewModel from './SpecificationAttributeUpdate.vm'

function SpecificationAttributeUpdate() {
    const {
        columns,
        form,
        modalForm,
        data,
        groupData,
        handleDeleteAttribute,
        handleSave,
        formatColorHex,
        handleColorChange,
        handleColorCheckChange,
        handleCancel,
        handleOk,
        color,
        editingOption,
        isColorPickerVisible,
        isModalVisible,
        setIsModalVisible,
        options,
        setOptions,
    } = useSpecificationAttributeUpdateViewModel()

    useEffect(() => {
        if (data) {
            form.setFieldsValue({
                name: data.name || '',
                group: data.specificationAttributeGroupId || 'none',
                displayOrder: data.displayOrder || 0,
            })

            if (data.listOptions) {
                const formattedOptions = data.listOptions.map((option) => ({
                    id: option.id || 0,
                    specificationAttribute:
                        typeof option.specificationAttribute === 'number' ? option.specificationAttribute : 0,
                    name: option.name || '',
                    displayOrder: option.displayOrder || 0,
                    associatedProducts: 0,
                    color: option.colorSquaresRgb || null,
                }))
                setOptions(formattedOptions)
            }
        }
    }, [data, form, setOptions])

    return (
        <>
            <div className='flex justify-between mb-5'>
                <div className='text-2xl font-medium '>
                    <div className=''>Edit specification attribute details</div>
                </div>
                <div className='flex gap-4 flex-wrap'>
                    <Button className='default-btn-color' size='large' icon={<SaveOutlined />} onClick={handleSave}>
                        {SpecificationAttributeConfigs.btnSave}
                    </Button>
                    <Button danger size='large' icon={<DeleteOutlined />} onClick={handleDeleteAttribute}>
                        {SpecificationAttributeConfigs.btnDelete}
                    </Button>
                </div>
            </div>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6'>
                <Form form={form} layout='vertical' initialValues={{ displayOrder: 0 }}>
                    <Form.Item
                        name='name'
                        label='Name'
                        rules={[
                            { required: true, message: 'Please input the group name!' },
                            { max: 100, message: 'Name cannot exceed 100 characters!' },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name='group'
                        label='Group'
                        rules={[{ required: true, message: 'Please select a group!' }]}
                    >
                        <Select placeholder='Select a group'>
                            <Select.Option value='none'>None</Select.Option>
                            {groupData?.map((group) => (
                                <Select.Option key={group.id} value={group.id}>
                                    {group.name}
                                </Select.Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        label='Display order'
                        name='displayOrder'
                        rules={[
                            { required: true, message: 'Please enter the display order!' },
                            {
                                type: 'number',
                                min: 0,
                                max: 2000000,
                                message: 'Display order must be between 0 and 2,000,000!',
                            },
                        ]}
                    >
                        <InputNumber type='number' />
                    </Form.Item>
                </Form>
                <h2>Options</h2>
                <Table columns={columns} dataSource={options} pagination={{ pageSize: 6 }} />
                <Button style={{ marginTop: '20px' }} onClick={() => setIsModalVisible(true)}>
                    Add a new option
                </Button>

                <Modal
                    title={editingOption ? 'Edit Option' : 'Add New Option'}
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    okText={editingOption ? 'Update' : 'Add'}
                >
                    <Form form={modalForm} layout='vertical'>
                        <Form.Item
                            name='optionName'
                            label='Name'
                            rules={[
                                { required: true, message: 'Please input the option name!' },
                                { max: 100, message: 'Name cannot exceed 100 characters!' },
                            ]}
                        >
                            <Input maxLength={101} />
                        </Form.Item>
                        <Form.Item label='Specify color'>
                            <Checkbox onChange={handleColorCheckChange} checked={isColorPickerVisible}>
                                Enable color picker
                            </Checkbox>
                            {isColorPickerVisible && (
                                <div style={{ marginTop: '10px' }}>
                                    <ColorPicker value={color} onChange={handleColorChange} />
                                    <div style={{ marginTop: '10px' }}>
                                        <strong>Selected Color:</strong> {formatColorHex(color)}
                                    </div>
                                </div>
                            )}
                        </Form.Item>
                        <Form.Item
                            label='Display order'
                            name='optionDisplayOrder'
                            rules={[
                                { required: true, message: 'Please enter the display order!' },
                                {
                                    type: 'number',
                                    min: 0,
                                    max: 2000000,
                                    message: 'Display order must be between 0 and 2,000,000!',
                                },
                            ]}
                        >
                            <InputNumber defaultValue={0} type='number' />
                        </Form.Item>
                    </Form>
                </Modal>
            </div>
        </>
    )
}

export default SpecificationAttributeUpdate
