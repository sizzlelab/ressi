require 'json'

class ServiceEventsController < ApplicationController
  before_filter :check_parameters_length
  
  def create
    # TODO: Check the incoming data for nil values etc.
    
    # Converting parameter 'headers' to Ruby-style associative array
    begin
      headers = JSON.parse(params[:service_event]["headers"])
    rescue JSON::ParserError => e
      Rails.logger.error "Encountered an error while parsing headers JSON. Ignoring headers for this event. #{e.message}"
      headers = {"HTTP_USER_AGENT" => "unknown","REQUEST_URI" => "unknown", "HTTP_REFERER" => "unknown"}
    rescue TypeError => e
      Rails.logger.error "Headers seem to be nil for this event. Ignoring them for this event. (error message: #{e.message})"
      headers = {"HTTP_USER_AGENT" => "unknown","REQUEST_URI" => "unknown", "HTTP_REFERER" => "unknown"}
    end
    
    @service_event = ServiceEvent.new(:user_id => params[:service_event]["user_id"],
                              :application_id => params[:service_event]["application_id"],
                              :session_id => params[:service_event]["session_id"],
                              :ip_address => params[:service_event]["ip_address"],

                              :action => params[:service_event]["action"],
                              :parameters => params[:service_event]["parameters"],
                              :return_value => params[:service_event]["return_value"],
                              :semantic_event_id => params[:service_event]["semantic_event_id"],
                              :http_user_agent => headers["HTTP_USER_AGENT"],
                              :request_uri => headers["REQUEST_URI"],
                              :http_referer => headers["HTTP_REFERER"],
                              :test_group_number => params[:service_event]["test_group_number"],
                              :created_at => params[:service_event]["created_at"]
                            )

    @service_event.save

    render :xml => @service_event.to_xml, :status => :created, 
          :location => "http://localhost:9000/service_event/#{@service_event.id}"

  end
  
  private
  def check_parameters_length
    params[:service_event]["parameters"] = "{}" if params[:service_event]["parameters"].length > 10000
  end

end

