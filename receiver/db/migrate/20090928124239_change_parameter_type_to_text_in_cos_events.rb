class ChangeParameterTypeToTextInCosEvents < ActiveRecord::Migration
  def self.up
    change_column :cos_events, :parameters, :text
  end

  def self.down
    change_column :cos_events, :parameters, :string
  end
end
