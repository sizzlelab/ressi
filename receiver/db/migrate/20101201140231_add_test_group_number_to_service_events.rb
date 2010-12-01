class AddTestGroupNumberToServiceEvents < ActiveRecord::Migration
  def self.up
    add_column :service_events, :test_group_number, :integer
  end

  def self.down
    remove_column :service_events, :test_group_number
  end
end
